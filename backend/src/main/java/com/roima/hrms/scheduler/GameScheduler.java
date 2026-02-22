package com.roima.hrms.scheduler;

import com.roima.hrms.entities.GameOperationHour;
import com.roima.hrms.entities.GameSlot;
import com.roima.hrms.entities.GameType;
import com.roima.hrms.entities.SlotBooking;
import com.roima.hrms.enums.SlotBookingStatusEnum;
import com.roima.hrms.repository.GameOperationHourRepository;
import com.roima.hrms.repository.GameSlotRepository;
import com.roima.hrms.repository.GameTypeRepository;
import com.roima.hrms.repository.SlotBookingRepository;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.spi.Limit;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class GameScheduler {

    private final int fixedDelay = 3000000;
    private final int initialDelay = 3000;
    private final long BUFFER_FOR_AUTO_CONFIRM = 30000; // using minutes directly
    private final GameTypeRepository gameTypeRepository;
    private final GameSlotRepository gameSlotRepository;
    private final GameOperationHourRepository gameOperationHourRepository;
    private final SlotBookingRepository slotBookingRepository;

    public GameScheduler(GameTypeRepository gameTypeRepository, GameSlotRepository gameSlotRepository,
            GameOperationHourRepository gameOperationHourRepository,
            SlotBookingRepository slotBookingRepository) {
        this.gameTypeRepository = gameTypeRepository;
        this.gameSlotRepository = gameSlotRepository;
        this.gameOperationHourRepository = gameOperationHourRepository;
        this.slotBookingRepository = slotBookingRepository;
    }

    @Scheduled(fixedDelay = fixedDelay, initialDelay = initialDelay)
    public void execute() {
        System.out.println("Fixed Delay Task Executed at: " + new Date());

        // create slots
        List<GameType> games = gameTypeRepository.findAll();
        for (GameType game : games) {
            createSlot(game.getId());
        }

        autoConfirmSLot();
    }

    public void createSlot(Long gameId) {
        // getting game
        GameType gameType = gameTypeRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game type not found"));

        // getting slot last generated slot
        GameSlot lastSlot = gameSlotRepository.findFirstByGameTypeIdOrderBySlotEndDesc(gameId).orElseThrow();

        if (lastSlot == null) {
            log.info("No slot configured for game");
        }

        // create slots from now upto the last slot + 1 less then 24
        int slotBufferSize = 24;
        int intervalMinuts = gameType.getMaxSlotDurationMinutes();

        // Operational hours
        List<GameOperationHour> opHrs = gameOperationHourRepository.findAllByGameType(gameType);

        LocalDateTime currentTime = lastSlot != null
                ? lastSlot.getSlotEnd().minusMinutes(Long.valueOf(gameType.getMaxSlotDurationMinutes()))
                : LocalDateTime.now();

        var currentNewSlotTime = roundUpToNextInterval(LocalDateTime.now().plusHours(24L), intervalMinuts);
        var interval = ChronoUnit.MINUTES.between(currentNewSlotTime, lastSlot.getSlotEnd());

        var totalSlots = (interval / gameType.getMaxSlotDurationMinutes());
        // var remainBuffer = Math.abs(totalSlots % slotBufferSize);

        log.info("new slots");
        // for (int i = 1; i < remainBuffer + 1; i++) {
        for (int i = 1; i < Math.abs(totalSlots) + 1; i++) {
            // todo: check for week days

            var time = roundUpToNextInterval(currentTime, intervalMinuts * i);

            // check if in operational hours
            boolean timeConflict = opHrs.stream().filter(opHr -> opHr != null).anyMatch(opHr -> {
                var opStartDateTime = LocalDateTime.of(time.toLocalDate(), opHr.getStartTime());
                var opEndDateTime = LocalDateTime.of(time.toLocalDate(), opHr.getEndTime());
                boolean afterTime = time.isAfter(opEndDateTime.minusMinutes(Long.valueOf(intervalMinuts)));
                boolean beforeTime = time.isBefore(opStartDateTime);
                return time.isAfter(opEndDateTime.minusMinutes(Long.valueOf(intervalMinuts)))
                        || time.isBefore(opStartDateTime);
            });
            // continue if time conflict
            if (timeConflict) {
                continue;
            } else {

                log.info("time " + i + ":" + time + " = " + time.plusMinutes(Long.valueOf(intervalMinuts)));
                GameSlot slot = GameSlot.builder().gameType(gameType).slotStart(time)
                        .slotEnd(time.plusMinutes(Long.valueOf(intervalMinuts))).build();
                gameSlotRepository.save(slot);
            }
        }
    }

    public void autoConfirmSLot() {
        LocalDateTime fifteenMinBuffer = LocalDateTime.now().plusMinutes(BUFFER_FOR_AUTO_CONFIRM);

        List<SlotBooking> slotBookings = slotBookingRepository.findNextBookings(LocalDateTime.now(), fifteenMinBuffer);

        for (SlotBooking slotBooking : slotBookings) {
            slotBooking.setStatus(SlotBookingStatusEnum.Confirmed);
            slotBookingRepository.save(slotBooking);
        }

    }

    private static LocalDateTime roundUpToNextInterval(LocalDateTime dateTime, int intervalMinutes) {

        // remoing seconds
        dateTime = dateTime.truncatedTo(ChronoUnit.MINUTES);

        // getting minute
        int minute = dateTime.getMinute();

        int remainder = minute % intervalMinutes;

        if (remainder == 0) {
            return dateTime.plusMinutes(intervalMinutes);
        } else {
            int minutesToAdd = intervalMinutes - remainder;
            return dateTime.plusMinutes(minutesToAdd);
        }
    }
}
