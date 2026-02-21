package com.roima.hrms.services;

import com.roima.hrms.dtos.res.AllGameSlotsDto;
import com.roima.hrms.dtos.res.GameSlotDto;
import com.roima.hrms.dtos.res.PlayerGroupDto;
import com.roima.hrms.dtos.res.SlotDetailsDto;
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
    private final GameQueueRepository gameQueueRepository;

    public GameSchedulingService(RoleUtil roleUtil, EmployeeProfileRepository employeeProfileRepository,
            PlayerGroupRepository playerGroupRepository, SlotBookingRepository slotBookingRepository,
            GameTypeRepository gameTypeRepository, GameSlotRepository gameSlotRepository, ModelMapper modelMapper,
            GameQueueRepository gameQueueRepository) {
        this.roleUtil = roleUtil;
        this.employeeProfileRepository = employeeProfileRepository;
        this.playerGroupRepository = playerGroupRepository;
        this.slotBookingRepository = slotBookingRepository;
        this.gameTypeRepository = gameTypeRepository;
        this.gameSlotRepository = gameSlotRepository;
        this.modelMapper = modelMapper;
        this.gameQueueRepository = gameQueueRepository;
    }

    public List<AllGameSlotsDto> getAllSlots() {
        List<GameType> games = gameTypeRepository.findAll();

        List<AllGameSlotsDto> allGameSlotsDtoList = new ArrayList<>();

        for (GameType game : games) {
            List<GameSlot> slots = gameSlotRepository.findAllByGameTypeFromNow(game.getId(), LocalDateTime.now());
            List<GameSlotDto> slotDtos = slots
                    .stream()
                    .map(gameSlot -> {
                        // making dto
                        GameSlotDto gameSlotDto = modelMapper.map(gameSlot, GameSlotDto.class);
                        SlotBooking slotsBooked = slotBookingRepository.findByGameSlot(gameSlot).orElse(null);
                        if (slotsBooked != null) {
                            gameSlotDto.setBooked(true);
                            // getting priority less or high
                            boolean isHigh = compareHighPriorityByProfileGame(roleUtil.getCurrentEmployee(),
                                    slotsBooked.getGroupOwner(), gameSlot.getGameType());
                            gameSlotDto.setLowPriority(!isHigh);
                        } else {
                            gameSlotDto.setLowPriority(true);
                            gameSlotDto.setBooked(false);
                        }
                        return gameSlotDto;
                    })
                    .toList();

            AllGameSlotsDto mapSLot = AllGameSlotsDto.builder()
                    .gameTypeId(game.getId())
                    .gameTypeName(game.getName())
                    .gameSlots(slotDtos)
                    .build();
            allGameSlotsDtoList.add(mapSLot);
        }

        return allGameSlotsDtoList;
    }

    public SlotDetailsDto getSlotDetails(Long slotId) {

        // getting required details
        GameSlot gameSlot = gameSlotRepository.findById(slotId)
                .orElseThrow(() -> new RuntimeException("Game Slot not found."));
        SlotBooking slotBooking = slotBookingRepository.findByGameSlot(gameSlot).orElse(null);

        // creating details seperatly

        GameSlotDto gameSlotDto = GameSlotDto.builder()
                .id(gameSlot.getId())
                .gameTypeId(gameSlot.getGameType().getId())
                .slotStart(
                        gameSlot.getSlotStart())
                .slotEnd(gameSlot.getSlotEnd())
                .isBooked(false)
                .isLowPriority(false)
                .build();

        SlotDetailsDto slotDetailsDto = SlotDetailsDto.builder().gameSlot(gameSlotDto).build();

        if (slotBooking != null) {
            gameSlotDto.setBooked(true);
            boolean isHigh = compareHighPriorityByProfileGame(roleUtil.getCurrentEmployee(),
                    slotBooking.getGroupOwner(), gameSlot.getGameType());
            gameSlotDto.setLowPriority(!isHigh);
        }

        // check active and put canBook

        return slotDetailsDto;

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

        // check already active booking for the player
        boolean slotActive = checkActiveBooking(roleUtil.getCurrentEmployee(), gameSlot.getGameType());

        if (slotActive) {
            // todo: throw error
            log.warn("active booking");
        }

        // todo: get priority
        int priority = getPriority(
                roleUtil.getCurrentEmployee(),
                gameTypeRepository.findByName(gameSlot.getGameType().getName()).orElseThrow());

        // todo: check priority
        SlotBooking newSlotBooking = SlotBooking.builder()
                .playerGroup(playerGroup)
                .gameSlot(gameSlot)
                .status(SlotBookingStatusEnum.Requested)
                .groupOwner(roleUtil.getCurrentEmployee())
                .build();

        // if no one has already requested that slot
        if (!slotBookingRepository.existsSlotBookingByGameSlot(gameSlot)) {
            // dont check if no booking in that slot
            // create booking here
            slotBookingRepository.save(newSlotBooking);
            return true;
        }

        // checking pririoty based on booked and new slotBooking request
        SlotBooking oldSlotBooking = slotBookingRepository.findByGameSlot(gameSlot).orElseThrow(()->new RuntimeException("Internal Logic error"));
        boolean isNewPriorityHigh = compareNewHighPriority(newSlotBooking, oldSlotBooking);
        // this will not null if swapped
        GameSlot tmp = null;
        if (isNewPriorityHigh) {
            // swap slots as new request is of more priority
            tmp = oldSlotBooking.getGameSlot();
            oldSlotBooking.setGameSlot(newSlotBooking.getGameSlot());
            newSlotBooking.setGameSlot(tmp);

            // book slot to new person
            slotBookingRepository.save(newSlotBooking);

            // add the swapped one to queue until confirmed
            GameQueue gameQueue = GameQueue.builder()
                    .slot(oldSlotBooking.getGameSlot())
                    .playerGroup(oldSlotBooking.getPlayerGroup())
                    .owner(oldSlotBooking.getGroupOwner())
                    .requestedOn(LocalDateTime.now())
                    .priorityAtRequest(
                            getPriority(oldSlotBooking.getGroupOwner(), oldSlotBooking.getGameSlot().getGameType()))
                    .build();

            gameQueueRepository.save(gameQueue);

        } else {

            // add in queue
            GameQueue gameQueue = GameQueue.builder()
                    .slot(newSlotBooking.getGameSlot())
                    .playerGroup(newSlotBooking.getPlayerGroup())
                    .owner(newSlotBooking.getGroupOwner())
                    .requestedOn(LocalDateTime.now())
                    .priorityAtRequest(priority)
                    .build();

            gameQueueRepository.save(gameQueue);
        }

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

    public boolean compareHighPriorityByProfileGame(EmployeeProfile player1, EmployeeProfile player2, GameType game) {
        int player1Priority = getPriority(player1, game);
        int player2Priority = getPriority(player2, game);
        return player1Priority > player2Priority;
    }

    public boolean checkActiveBooking(EmployeeProfile player, GameType game) {
        boolean activeBooking = slotBookingRepository.existsSlotBookingByPlayerAndGame(player, game,
                LocalDateTime.now());
        return activeBooking;
    }

}
