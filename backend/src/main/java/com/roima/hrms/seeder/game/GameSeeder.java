package com.roima.hrms.seeder.game;

import com.roima.hrms.entities.*;
import com.roima.hrms.enums.RoleEnum;
import com.roima.hrms.exceptions.EmployeeNotFoundException;
import com.roima.hrms.repository.*;
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
    private final PlayerGroupRepository playerGroupRepository;
    private final EmployeeProfileRepository employeeProfileRepository;

    public GameSeeder(GameTypeRepository gameTypeRepository, GameSlotRepository gameSlotRepository, GameOperationHourRepository gameOperationHourRepository, PlayerGroupRepository playerGroupRepository, EmployeeProfileRepository employeeProfileRepository) {
        this.gameTypeRepository = gameTypeRepository;
        this.gameSlotRepository = gameSlotRepository;
        this.gameOperationHourRepository = gameOperationHourRepository;
        this.playerGroupRepository = playerGroupRepository;
        this.employeeProfileRepository = employeeProfileRepository;
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
            LocalDateTime startTime = LocalDateTime.of(LocalDate.now(), opHrs.getStartTime());
            var start = roundUpToNextInterval(startTime, pool.getMaxSlotDurationMinutes());
            GameSlot slot = GameSlot.builder()
                    .gameType(pool)
                    .slotStart(start)
                    .slotEnd(start.plusMinutes(Long.valueOf(pool.getMaxSlotDurationMinutes())))
                    .build();
            gameSlotRepository.save(slot);
        }

        // initial group
        if (!playerGroupRepository.existsById(1L)) {
            EmployeeProfile p1 = employeeProfileRepository.findById(1L).orElseThrow(EmployeeNotFoundException::new);
            EmployeeProfile p2 = employeeProfileRepository.findById(2L).orElseThrow(EmployeeNotFoundException::new);
            EmployeeProfile p3 = employeeProfileRepository.findById(3L).orElseThrow(EmployeeNotFoundException::new);
            EmployeeProfile p4 = employeeProfileRepository.findById(4L).orElseThrow(EmployeeNotFoundException::new);

            PlayerGroup playerGroup1 = PlayerGroup.builder().players(List.of(p1, p2, p3, p4)).build();

            playerGroupRepository.save(playerGroup1);
        }
        if (!playerGroupRepository.existsById(2L)) {
            EmployeeProfile p5 = employeeProfileRepository.findById(5L).orElseThrow(EmployeeNotFoundException::new);
            EmployeeProfile p6 = employeeProfileRepository.findById(6L).orElseThrow(EmployeeNotFoundException::new);
            EmployeeProfile p7 = employeeProfileRepository.findById(7L).orElseThrow(EmployeeNotFoundException::new);
            EmployeeProfile p8 = employeeProfileRepository.findById(8L).orElseThrow(EmployeeNotFoundException::new);

            PlayerGroup playerGroup2 = PlayerGroup.builder().players(List.of(p5, p6, p7, p8)).build();

            playerGroupRepository.save(playerGroup2);
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
