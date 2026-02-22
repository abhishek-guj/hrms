package com.roima.hrms.services;

import com.roima.hrms.dtos.req.SlotBookingReqDto;
import com.roima.hrms.dtos.res.AllGameSlotsDto;
import com.roima.hrms.dtos.res.GameSlotDto;
import com.roima.hrms.dtos.res.PlayerGroupDto;
import com.roima.hrms.dtos.res.SlotBookingDto;
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

    private final GameSlotSizeRepository gameSlotSizeRepository;

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
            GameQueueRepository gameQueueRepository, GameSlotSizeRepository gameSlotSizeRepository) {
        this.roleUtil = roleUtil;
        this.employeeProfileRepository = employeeProfileRepository;
        this.playerGroupRepository = playerGroupRepository;
        this.slotBookingRepository = slotBookingRepository;
        this.gameTypeRepository = gameTypeRepository;
        this.gameSlotRepository = gameSlotRepository;
        this.modelMapper = modelMapper;
        this.gameQueueRepository = gameQueueRepository;
        this.gameSlotSizeRepository = gameSlotSizeRepository;
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

    public List<SlotBookingDto> getBookings() {
        List<SlotBooking> slotBookings = slotBookingRepository.findAllByPlayer(roleUtil.getCurrentEmployeeId());

        // List<SlotBookingDto> slotBookingDtos = slotBookings
        // .stream()
        // .map(booking -> {
        // Set<GameSlotSize> slotSizes = gameSlotSizeRepository
        // .findAllByGameTypeOrderBySlotSize(booking.getGameSlot());
        // SlotBookingDto dto = modelMapper.map(booking, SlotBookingDto.class);
        // dto.setSlotSizes(slotSizes);
        // return dto;
        // })
        // .toList();
        // return slotBookingDtos;
        return slotBookings
                .stream()
                .map(booking -> {
                    Set<GameSlotSize> slotSizes = gameSlotSizeRepository
                            .findAllByGameTypeOrderBySlotSize(booking.getGameSlot().getGameType());
                    SlotBookingDto dto = modelMapper.map(booking, SlotBookingDto.class);
                    dto.setInQueue(false);
                    dto.setSlotSizes(slotSizes.stream().map(slotSize -> slotSize.getSlotSize()).toList());

                    List<GameQueue> isInQueue = gameQueueRepository.findByGameSlotAndOwner(
                            booking.getGameSlot().getId(),
                            roleUtil.getCurrentEmployee());

                    if (!isInQueue.isEmpty()) {

                        dto.setInQueue(true);
                    }
                    dto.setQueueSize(isInQueue.size());
                    return dto;
                })
                .toList();
    }

    public SlotDetailsDto getSlotDetails(Long slotId) {

        // getting required details
        GameSlot gameSlot = gameSlotRepository.findById(slotId)
                .orElseThrow(() -> new RuntimeException("Game Slot not found."));
        SlotBooking slotBooking = slotBookingRepository.findByGameSlot(gameSlot).orElse(null);

        // creating details seperatly
        Set<GameSlotSize> slotSizes = gameSlotSizeRepository.findAllByGameTypeOrderBySlotSize(gameSlot.getGameType());

        GameSlotDto gameSlotDto = GameSlotDto.builder()
                .id(gameSlot.getId())
                .gameTypeId(gameSlot.getGameType().getId())
                .gameTypeName(gameSlot.getGameType().getName())
                .slotStart(
                        gameSlot.getSlotStart())
                .slotEnd(gameSlot.getSlotEnd())
                .isBooked(false)
                .isLowPriority(false)
                .build();

        boolean canBook = true;

        SlotDetailsDto slotDetailsDto = SlotDetailsDto
                .builder()
                .gameSlot(gameSlotDto)
                .slotSizes(slotSizes.stream().map(slotSize -> slotSize.getSlotSize()).toList())
                .build();

        if (slotBooking != null) {
            gameSlotDto.setBooked(true);
            boolean isHigh = compareHighPriorityByProfileGame(roleUtil.getCurrentEmployee(),
                    slotBooking.getGroupOwner(),
                    gameSlot.getGameType());
            gameSlotDto.setLowPriority(!isHigh);

            boolean checkIfPlayer = slotBooking.getPlayerGroup().getPlayers().contains(roleUtil.getCurrentEmployee());
            slotDetailsDto.setInGroup(checkIfPlayer);
        }

        boolean hasActive = checkActiveBooking(roleUtil.getCurrentEmployee(), gameSlot.getGameType());

        if (hasActive) {
            canBook = false;
        }

        try {

            List<GameQueue> isInQueue = gameQueueRepository.findByGameSlotAndOwner(gameSlot.getId(),
                    roleUtil.getCurrentEmployee());
            // check active and put canBook
            if (!isInQueue.isEmpty()) {
                canBook = false;
                slotDetailsDto.setInQueue(true);
                slotDetailsDto.setQueueSize(isInQueue.size());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex.getMessage());
        }

        slotDetailsDto.setCanBook(canBook);
        return slotDetailsDto;

    }

    @Transactional
    public void deleteBookingOrQueue(Long gameSlotId) {
        EmployeeProfile currentPlayer = roleUtil.getCurrentEmployee();

        GameSlot gameSlot = gameSlotRepository.findById(gameSlotId)
                .orElseThrow(() -> new RuntimeException("Game Slot not found"));

        SlotBooking slotBooking = slotBookingRepository.findByGameSlotDelete(gameSlot).orElse(null);

        if (slotBooking != null) {
            EmployeeProfile owner = slotBooking.getGroupOwner();
            if (owner.getId() == roleUtil.getCurrentEmployeeId()) {
                slotBooking.setStatus(SlotBookingStatusEnum.Cancelled);
                allocateFromQueue(gameSlot);
                return;
            }

        }

        GameQueue queue = gameQueueRepository.findBySlotAndOwner(gameSlot, currentPlayer).orElse(null);

        if (queue != null) {
            gameQueueRepository.delete(queue);
            return;
        }

        throw new RuntimeException("No Booking found for slot not in queue also.");
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

    public boolean bookSlot(Long gameSlotId, SlotBookingReqDto slotBookingReqDto) {

        // gameSlot
        GameSlot gameSlot = gameSlotRepository.findById(gameSlotId)
                .orElseThrow(() -> new RuntimeException("Game Slot not found."));
        // player group
        PlayerGroup playerGroup = createPlayerGroup(slotBookingReqDto.getPlayerIds());

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
        SlotBooking oldSlotBooking = slotBookingRepository.findByGameSlot(gameSlot)
                .orElseThrow(() -> new RuntimeException("Internal Logic error"));
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

    @Transactional
    public void bookSLotNew(Long gameSlotId, SlotBookingReqDto slotBookingReqDto) {
        EmployeeProfile currentPlayer = roleUtil.getCurrentEmployee();

        // gameSlot
        GameSlot gameSlot = gameSlotRepository.findById(gameSlotId)
                .orElseThrow(() -> new RuntimeException("Game Slot not found."));

        // checking before slot
        if (gameSlot.getSlotStart().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Cant book slot after start timeing");
        }

        boolean slotActive = checkActiveBooking(roleUtil.getCurrentEmployee(), gameSlot.getGameType());
        if (slotActive) {
            throw new RuntimeException("cant book already active slot");
        }

        // player group
        PlayerGroup playerGroup = createPlayerGroup(slotBookingReqDto.getPlayerIds());

        int newPriority = getPriority(
                roleUtil.getCurrentEmployee(),
                gameTypeRepository.findByName(gameSlot.getGameType().getName()).orElseThrow());

        boolean isActiveSlot = slotBookingRepository.existsSlotBookingByGameSlot(gameSlot);

        // if no slot there
        if (!isActiveSlot) {
            SlotBooking newSlotBooking = SlotBooking.builder()
                    .playerGroup(playerGroup)
                    .gameSlot(gameSlot)
                    .status(SlotBookingStatusEnum.Requested)
                    .groupOwner(roleUtil.getCurrentEmployee())
                    .build();

            slotBookingRepository.save(newSlotBooking);
            return;
        }

        // so therer is a slot booking
        SlotBooking oldSlotBooking = slotBookingRepository.findByGameSlot(gameSlot)
                .orElseThrow(() -> new RuntimeException("Internal Logic error"));

        // if confirmed cant do anything adding in queue
        if (oldSlotBooking.getStatus() == SlotBookingStatusEnum.Confirmed) {
            addToQueue(gameSlot, playerGroup, currentPlayer, newPriority);
            return;
        }

        // checking pririoty based on booked and new slotBooking request

        int oldPriority = getPriority(oldSlotBooking.getGroupOwner(), gameSlot.getGameType());

        if (newPriority < oldPriority) {
            slotBookingRepository.delete(oldSlotBooking);

            addToQueue(gameSlot, oldSlotBooking.getPlayerGroup(), oldSlotBooking.getGroupOwner(), oldPriority);

            SlotBooking newSlotBooking = SlotBooking.builder()
                    .gameSlot(gameSlot)
                    .playerGroup(playerGroup)
                    .groupOwner(currentPlayer)
                    .status(SlotBookingStatusEnum.Requested)
                    .build();

            SlotBooking saved = slotBookingRepository.save(newSlotBooking);
        } else {
            addToQueue(gameSlot, playerGroup, currentPlayer, newPriority);
        }
    }

    // CANCEL BOOKING
    //

    // helper functions
    @Transactional
    public void addToQueue(
            GameSlot gameSlot,
            PlayerGroup playerGroup,
            EmployeeProfile owner,
            int priority) {
        //
        GameQueue queue = GameQueue.builder()
                .slot(gameSlot)
                .playerGroup(playerGroup)
                .owner(owner)
                .priorityAtRequest(priority)
                .requestedOn(LocalDateTime.now())
                .build();

        gameQueueRepository.save(queue);
    }

    @Transactional
    public void allocateFromQueue(GameSlot gameSlot) {
        List<GameQueue> queues = gameQueueRepository.findBySlotOrderByPriorityAtRequest(gameSlot);

        if (queues.isEmpty()) {
            return;
        }

        // geting top priority bokking req
        GameQueue highGameQueue = queues.get(0);

        SlotBooking slotBooking = SlotBooking.builder()
                .gameSlot(gameSlot)
                .playerGroup(highGameQueue.getPlayerGroup())
                .groupOwner(highGameQueue.getOwner())
                .status(SlotBookingStatusEnum.Requested)
                .build();

        slotBookingRepository.save(slotBooking);

        // deleting it
        gameQueueRepository.delete(highGameQueue);
    }

    public int getPriority(EmployeeProfile player, GameType game) {

        Set<SlotBooking> playerGames = slotBookingRepository.findAllByPlayerAndGamePast24HrsConfirmed(
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
