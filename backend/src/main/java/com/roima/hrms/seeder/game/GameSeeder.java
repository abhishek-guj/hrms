package com.roima.hrms.seeder.game;

import com.roima.hrms.entities.*;
import com.roima.hrms.enums.RoleEnum;
import com.roima.hrms.enums.SlotBookingStatusEnum;
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
    private final SlotBookingRepository slotBookingRepository;

    public GameSeeder(GameTypeRepository gameTypeRepository, GameSlotRepository gameSlotRepository, GameOperationHourRepository gameOperationHourRepository, PlayerGroupRepository playerGroupRepository, EmployeeProfileRepository employeeProfileRepository, SlotBookingRepository slotBookingRepository) {
        this.gameTypeRepository = gameTypeRepository;
        this.gameSlotRepository = gameSlotRepository;
        this.gameOperationHourRepository = gameOperationHourRepository;
        this.playerGroupRepository = playerGroupRepository;
        this.employeeProfileRepository = employeeProfileRepository;
        this.slotBookingRepository = slotBookingRepository;
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
        if (!gameTypeRepository.existsByName("chess")) {
            GameType pool = GameType.builder()
                    .name("chess")
                    .maxPlayers(2)
                    .minPlayers(2)
                    .maxSlotDurationMinutes(30)
                    .build();

            gameTypeRepository.save(pool);
        }


        GameType pool = gameTypeRepository.findByName("pool").orElseThrow();
        GameType chess = gameTypeRepository.findByName("chess").orElseThrow();

        // operational hours
        if (!gameOperationHourRepository.existsByGameType(pool)) {
            GameOperationHour opHour = GameOperationHour.builder()
                    .gameType(pool)
                    .startTime(LocalTime.of(10, 0, 0))
                    .endTime(LocalTime.of(22, 0, 0))
                    .build();
            gameOperationHourRepository.save(opHour);
        }
        if (!gameOperationHourRepository.existsByGameType(chess)) {
            GameOperationHour opHour = GameOperationHour.builder()
                    .gameType(chess)
                    .startTime(LocalTime.of(12, 0, 0))
                    .endTime(LocalTime.of(18, 0, 0))
                    .build();
            gameOperationHourRepository.save(opHour);
        }

        // initial slot
        if (!gameSlotRepository.existsByGameType(pool)) {
            GameOperationHour opHrs = gameOperationHourRepository.findFirstByGameTypeOrderByStartTime(pool);
            LocalDateTime startTime = LocalDateTime.of(LocalDate.now(), opHrs.getStartTime().minusMinutes(Long.valueOf(pool.getMaxSlotDurationMinutes())));
            var start = roundUpToNextInterval(startTime, pool.getMaxSlotDurationMinutes());
            GameSlot slot = GameSlot.builder()
                    .gameType(pool)
                    .slotStart(start)
                    .slotEnd(start.plusMinutes(Long.valueOf(pool.getMaxSlotDurationMinutes())))
                    .build();
            gameSlotRepository.save(slot);
        }
        if (!gameSlotRepository.existsByGameType(chess)) {
            GameOperationHour opHrs = gameOperationHourRepository.findFirstByGameTypeOrderByStartTime(chess);
            LocalDateTime startTime = LocalDateTime.of(LocalDate.now(), opHrs.getStartTime().minusMinutes(Long.valueOf(chess.getMaxSlotDurationMinutes())));
            var start = roundUpToNextInterval(startTime, chess.getMaxSlotDurationMinutes());
            GameSlot slot = GameSlot.builder()
                    .gameType(chess)
                    .slotStart(start)
                    .slotEnd(start.plusMinutes(Long.valueOf(chess.getMaxSlotDurationMinutes())))
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

        PlayerGroup pg1 = playerGroupRepository.findById(1L).orElseThrow();
        GameSlot gameSlot = gameSlotRepository.findBySlotStartAndGameType_Name(LocalDateTime.of(2026, 02, 21, 13, 00, 00), "pool");
        EmployeeProfile owner1 = employeeProfileRepository.findById(1L).orElseThrow(EmployeeNotFoundException::new);
        if (!slotBookingRepository.existsById(1L)) {
            SlotBooking slotBooking1 = SlotBooking.builder()
                    .playerGroup(pg1)
                    .gameSlot(gameSlot)
                    .status(SlotBookingStatusEnum.Requested)
                    .groupOwner(owner1)
                    .build();

        slotBookingRepository.save(slotBooking1);
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
