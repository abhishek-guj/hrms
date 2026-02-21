package com.roima.hrms.services;

import com.roima.hrms.dtos.res.AllGameSlotsDto;
import com.roima.hrms.dtos.res.GameSlotDto;
import com.roima.hrms.entities.*;
import com.roima.hrms.enums.SlotBookingStatusEnum;
import com.roima.hrms.exceptions.EmployeeNotFoundException;
import com.roima.hrms.repository.*;
import com.roima.hrms.utils.RoleUtil;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

import static javax.swing.UIManager.put;

@Slf4j
@Service
public class GameSchedulingService {

    private final RoleUtil roleUtil;
    private final EmployeeProfileRepository employeeProfileRepository;
    private final PlayerGroupRepository playerGroupRepository;
    private final SlotBookingRepository slotBookingRepository;
    private final GameTypeRepository gameTypeRepository;
    private final GameSlotRepository gameSlotRepository;
    private final ModelMapper modelMapper;

    // constants
    private final long PRIORITY_CHECK_DAYS = 3L;

    public GameSchedulingService(RoleUtil roleUtil, EmployeeProfileRepository employeeProfileRepository,
            PlayerGroupRepository playerGroupRepository, SlotBookingRepository slotBookingRepository,
            GameTypeRepository gameTypeRepository, GameSlotRepository gameSlotRepository, ModelMapper modelMapper) {
        this.roleUtil = roleUtil;
        this.employeeProfileRepository = employeeProfileRepository;
        this.playerGroupRepository = playerGroupRepository;
        this.slotBookingRepository = slotBookingRepository;
        this.gameTypeRepository = gameTypeRepository;
        this.gameSlotRepository = gameSlotRepository;
        this.modelMapper = modelMapper;
    }

    public AllGameSlotsDto getAllSlots() {
        List<GameType> games = gameTypeRepository.findAll();

        AllGameSlotsDto allGameSlotsMap = AllGameSlotsDto.builder()
                .gameSlots(new HashMap<Long, List<GameSlotDto>>())
                .build();

        for (GameType game : games) {
            List<GameSlot> slots = gameSlotRepository.findAllByGameTypeFromNow(game.getId(), LocalDateTime.now());
            List<GameSlotDto> slotDtos = slots
                    .stream()
                    .map(gameSlot -> modelMapper.map(gameSlot, GameSlotDto.class))
                    .toList();

            allGameSlotsMap.getGameSlots().put(game.getId(), slotDtos);
        }

        return allGameSlotsMap;
    }

    @Transactional
    public PlayerGroup createPlayerGroup(List<Long> playerIds) {
        EmployeeProfile currentPlayer = roleUtil.getCurrentEmployee();

        PlayerGroup playerGroup = PlayerGroup.builder()
                .players(List.of(currentPlayer)).build();

        try {

            List<EmployeeProfile> groupPlayers = new ArrayList<>();
            playerIds.forEach(id -> {
                EmployeeProfile player = employeeProfileRepository.findById(id)
                        .orElseThrow(EmployeeNotFoundException::new);
                groupPlayers.add(player);
            });
            playerGroup.setPlayers(groupPlayers);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        playerGroupRepository.save(playerGroup);

        return playerGroup;
    }

    public boolean bookSlot(GameSlot gameSlot, PlayerGroup playerGroup) {

        // check already active booking
        boolean slotActive = checkActiveBooking(roleUtil.getCurrentEmployee(), gameSlot.getGameType());

        if (slotActive) {
            // todo: throw error
            log.warn("active booking");
        }
        // todo: get priority
        int priority = getPriority(
                roleUtil.getCurrentEmployee(),
                gameTypeRepository.findByName("pool").orElseThrow());

        // todo: check priority
        // // loop through slots from booking request slot
        // getting generated slots
        List<GameSlot> slots = gameSlotRepository.findAllByGameSlotAfter(gameSlot.getGameType(),
                gameSlot.getSlotStart());
        GameSlot tmp = null;
        SlotBooking newSlotBooking = SlotBooking.builder()
                .playerGroup(playerGroup)
                .gameSlot(gameSlot)
                .status(SlotBookingStatusEnum.Requested)
                .groupOwner(roleUtil.getCurrentEmployee())
                .build();
        for (GameSlot oldSlot : slots) {
            if (!slotBookingRepository.existsSlotBookingByGameSlot(oldSlot)) {
                // dont check if no booking in that slot
                // create booking here
                slotBookingRepository.save(newSlotBooking);
                return true;
            }

            SlotBooking oldSlotBooking = slotBookingRepository.findByGameSlot(oldSlot);

            boolean isNewPriorityHigh = compareNewHighPriority(newSlotBooking, oldSlotBooking);

            if (isNewPriorityHigh) {
                // change slots
                tmp = oldSlotBooking.getGameSlot();
                oldSlotBooking.setGameSlot(newSlotBooking.getGameSlot());
                newSlotBooking.setGameSlot(tmp);
            }

        }
        // todo: if high priority
        // book slot to new person
        // add
        // add in queue

        return false;
    }

    // CANCEL BOOKING
    //

    // helper functions
    public int getPriority(EmployeeProfile player, GameType game) {

        Set<SlotBooking> playerGames = slotBookingRepository.findAllByPlayerAndGamePast24Hrs(
                player,
                game,
                LocalDateTime.now().minusDays(PRIORITY_CHECK_DAYS),
                LocalDateTime.now());

        return playerGames.size();
    }

    public boolean compareNewHighPriority(SlotBooking newSlotBooking, SlotBooking oldSlotBooking) {

        int newSlotPriority = getPriority(newSlotBooking.getGroupOwner(), newSlotBooking.getGameSlot().getGameType());
        int oldSlotPriority = getPriority(oldSlotBooking.getGroupOwner(), oldSlotBooking.getGameSlot().getGameType());
        if (oldSlotPriority > newSlotPriority) {
            return true;
        }
        return false;
    }

    public boolean checkActiveBooking(EmployeeProfile player, GameType game) {
        boolean activeBooking = slotBookingRepository.existsSlotBookingByPlayerAndGame(player, game,
                LocalDateTime.now());
        return activeBooking;
    }
}
