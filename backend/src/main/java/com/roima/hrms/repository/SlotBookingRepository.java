package com.roima.hrms.repository;

import com.roima.hrms.entities.EmployeeProfile;
import com.roima.hrms.entities.GameSlot;
import com.roima.hrms.entities.GameType;
import com.roima.hrms.entities.SlotBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface SlotBookingRepository extends JpaRepository<SlotBooking, Long> {

        @Query("""
                        select sb from SlotBooking sb
                        join sb.playerGroup pg
                        join pg.players p
                        where p = :employeeProfile
                        and sb.gameSlot.gameType = :gameType
                        and sb.gameSlot.slotStart >= :thresholdStartDateTime
                        and sb.gameSlot.slotEnd <= :thresholdEndDateTime
                        and sb.isDeleted = false
                        """)
        Set<SlotBooking> findAllByPlayerAndGamePast24Hrs(EmployeeProfile employeeProfile, GameType gameType,
                        LocalDateTime thresholdStartDateTime, LocalDateTime thresholdEndDateTime);

        @Query("""
                        select sb from SlotBooking sb
                        join sb.playerGroup pg
                        join pg.players p
                        where p = :employeeProfile
                        and sb.gameSlot.gameType = :gameType
                        and sb.gameSlot.slotStart >= :thresholdStartDateTime
                        and sb.gameSlot.slotEnd <= :thresholdEndDateTime
                        and sb.isDeleted = false
                        and sb.status = "Confirmed"
                        """)
        Set<SlotBooking> findAllByPlayerAndGamePast24HrsConfirmed(EmployeeProfile employeeProfile, GameType gameType,
                        LocalDateTime thresholdStartDateTime, LocalDateTime thresholdEndDateTime);

        // count(s) > 0) from SlotBooking
        @Query("""
                        select (count(sb)>0) from SlotBooking sb
                        where sb.groupOwner = :player
                        and (sb.status = 'Requested' or sb.status = 'Waiting')
                        and sb.gameSlot.slotStart >= :thresholdDateTime
                        and sb.gameSlot.gameType = :gameType
                        and sb.isDeleted = false
                        """)
        boolean existsSlotBookingByPlayerAndGame(EmployeeProfile player, GameType gameType,
                        LocalDateTime thresholdDateTime);

        @Query("select sb from SlotBooking sb where sb.gameSlot = :gameSlot and (sb.status = 'Requested' or sb.status = 'Waiting') ")
        List<SlotBooking> findAllByGameSlot(GameSlot gameSlot);

        @Query("select sb from SlotBooking sb where sb.gameSlot = :gameSlot and (sb.status = 'Requested' or sb.status = 'Waiting') ")
        Optional<SlotBooking> findByGameSlot(GameSlot gameSlot);

        @Query("select sb from SlotBooking sb where sb.gameSlot = :gameSlot and (sb.status = 'Requested' or sb.status = 'Waiting' or sb.status = 'Confirmed') ")
        Optional<SlotBooking> findByGameSlotDelete(GameSlot gameSlot);

        @Query("select (count(sb)>0) from SlotBooking sb where sb.gameSlot = :gameSlot and (sb.status = 'Requested' or sb.status = 'Waiting')")
        boolean existsSlotBookingByGameSlot(GameSlot gameSlot);

        boolean existsByGameSlot(GameSlot gameSlot);

        @Query("""
                            select distinct sb,gs from SlotBooking sb
                            join sb.playerGroup pg
                            join pg.players p
                            join sb.gameSlot gs
                            where p.id = :playerId
                            and sb.isDeleted = false
                            order by gs.slotStart desc
                        """)
        List<SlotBooking> findAllByPlayer(Long playerId);

        @Query("""
                                select sb from SlotBooking sb
                                where sb.status = 'Requested'
                                and sb.gameSlot.slotStart between :startTime and :endTime
                        """)
        List<SlotBooking> findNextBookings(LocalDateTime startTime, LocalDateTime endTime);

}