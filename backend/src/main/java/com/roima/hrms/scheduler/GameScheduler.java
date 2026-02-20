package com.roima.hrms.scheduler;

import com.roima.hrms.entities.GameSlot;
import com.roima.hrms.entities.GameType;
import com.roima.hrms.repository.GameSlotRepository;
import com.roima.hrms.repository.GameTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.spi.Limit;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Component
public class GameScheduler {

    private final int fixedDelay = 3000;
    private final int initialDelay = 3000;
    private final GameTypeRepository gameTypeRepository;
    private final GameSlotRepository gameSlotRepository;

    public GameScheduler(GameTypeRepository gameTypeRepository, GameSlotRepository gameSlotRepository) {
        this.gameTypeRepository = gameTypeRepository;
        this.gameSlotRepository = gameSlotRepository;
    }

    @Scheduled(fixedDelay = fixedDelay, initialDelay = initialDelay)
    public void execute() {
        System.out.println("Fixed Delay Task Executed at: " + new Date());
        createSlot(1L);
    }

    public void createSlot(Long gameId) {
        // getting game
        GameType gameType = gameTypeRepository.findById(gameId).orElseThrow(() -> new RuntimeException("Game type not found"));

        // getting slot last generated slot
        GameSlot lastSlot = gameSlotRepository.findFirstByGameTypeIdOrderBySlotEndDesc(gameId).orElseThrow();

        if (lastSlot == null) {
            log.info("No slot configured for game");
        }

        // create slots from now upto the last slot + 1 less then 24
        int slotBufferSize = 24;
        int intervalMinuts = gameType.getMaxSlotDurationMinutes();

        LocalDateTime currentTime = lastSlot != null ? lastSlot.getSlotEnd() : LocalDateTime.now();

        var currentNewSlotTime = roundUpToNextInterval(LocalDateTime.now(), intervalMinuts);
        var interval = ChronoUnit.MINUTES.between(currentNewSlotTime, lastSlot.getSlotEnd());

        var remainBuffer = slotBufferSize - (interval / gameType.getMaxSlotDurationMinutes());

        log.info("new slots");
        for (int i = 1; i < remainBuffer + 1; i++) {
            var time = roundUpToNextInterval(currentTime, intervalMinuts * i);
            log.info("time " + i + ":" + time + " = " + time.plusMinutes(Long.valueOf(intervalMinuts)));
            GameSlot slot = GameSlot.builder().gameType(gameType).slotStart(time).slotEnd(time.plusMinutes(Long.valueOf(intervalMinuts))).build();
            gameSlotRepository.save(slot);
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
