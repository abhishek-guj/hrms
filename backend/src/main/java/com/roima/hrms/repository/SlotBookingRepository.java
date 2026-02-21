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
            where sb.groupOwner = :employeeProfile 
            and sb.gameSlot.gameType = :gameType 
            and sb.gameSlot.slotStart >= :thresholdStartDateTime
            and sb.gameSlot.slotEnd <= :thresholdEndDateTime
            """)
    Set<SlotBooking> findAllByPlayerAndGamePast24Hrs(EmployeeProfile employeeProfile, GameType gameType, LocalDateTime thresholdStartDateTime, LocalDateTime thresholdEndDateTime);

//    count(s) > 0) from SlotBooking
    @Query("""
            select (count(sb)>0) from SlotBooking sb 
            where sb.groupOwner = :player 
            and (sb.status = 'Requested' or sb.status = 'Waiting') 
            and sb.gameSlot.slotStart >= :thresholdDateTime 
            and sb.gameSlot.gameType = :gameType
            """)
    boolean existsSlotBookingByPlayerAndGame(EmployeeProfile player, GameType gameType, LocalDateTime thresholdDateTime);


    List<SlotBooking> findAllByGameSlot(GameSlot gameSlot);

    Optional<SlotBooking> findByGameSlot(GameSlot gameSlot);

    boolean existsSlotBookingByGameSlot(GameSlot gameSlot);

    boolean existsByGameSlot(GameSlot gameSlot);
}