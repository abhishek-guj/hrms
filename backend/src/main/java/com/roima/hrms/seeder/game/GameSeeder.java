package com.roima.hrms.seeder.game;

import com.roima.hrms.entities.GameOperationHour;
import com.roima.hrms.entities.GameSlot;
import com.roima.hrms.entities.GameType;
import com.roima.hrms.entities.Role;
import com.roima.hrms.enums.RoleEnum;
import com.roima.hrms.repository.GameOperationHourRepository;
import com.roima.hrms.repository.GameSlotRepository;
import com.roima.hrms.repository.GameTypeRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

@Component
@Order(99)
public class GameSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final GameTypeRepository gameTypeRepository;
    private final GameSlotRepository gameSlotRepository;
    private final GameOperationHourRepository gameOperationHourRepository;

    public GameSeeder(GameTypeRepository gameTypeRepository, GameSlotRepository gameSlotRepository, GameOperationHourRepository gameOperationHourRepository) {
        this.gameTypeRepository = gameTypeRepository;
        this.gameSlotRepository = gameSlotRepository;
        this.gameOperationHourRepository = gameOperationHourRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.loadGames();
    }

    public void loadGames() {
        if (!gameTypeRepository.existsByName("pool")) {
            GameType pool = GameType.builder()
                    .name("pool")
                    .maxPlayers(4)
                    .minPlayers(2)
                    .maxSlotDurationMinutes(60)
                    .build();

            gameTypeRepository.save(pool);


        }
        GameType pool = gameTypeRepository.findByName("pool").orElseThrow();

        // operational hours
        if (!gameOperationHourRepository.existsByGameType(pool)) {
            GameOperationHour opHour = GameOperationHour.builder()
                    .gameType(pool)
                    .startTime(LocalTime.of(10, 0, 0))
                    .endTime(LocalTime.of(22, 0, 0))
                    .build();
            gameOperationHourRepository.save(opHour);
        }

        // initial slot
        if (!gameSlotRepository.existsByGameType(pool)) {
            GameOperationHour opHrs = gameOperationHourRepository.findFirstByGameTypeOrderByStartTime(pool);
            LocalDateTime startTime = LocalDateTime.of(LocalDate.now(),opHrs.getStartTime());
            var start = roundUpToNextInterval(startTime, pool.getMaxSlotDurationMinutes());
            GameSlot slot = GameSlot.builder()
                    .gameType(pool)
                    .slotStart(start)
                    .slotEnd(start.plusMinutes(Long.valueOf(pool.getMaxSlotDurationMinutes())))
                    .build();
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
