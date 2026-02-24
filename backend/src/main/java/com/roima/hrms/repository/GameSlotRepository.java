package com.roima.hrms.repository;

import com.roima.hrms.entities.GameSlot;
import com.roima.hrms.entities.GameType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface GameSlotRepository extends JpaRepository<GameSlot, Long> {

    @Query("select gs from GameSlot gs where gs.gameType.id = :gameTypeId order by gs.slotEnd desc limit 1")
    Optional<GameSlot> findFirstByGameTypeIdOrderBySlotEndDesc(Long gameTypeId);

    boolean existsByGameType(GameType gameType);

    @Query("select gs from GameSlot gs where gs.gameType.id = :gameTypeId and gs.slotEnd>:currentDateTime")
    List<GameSlot> findAllByGameTypeFromNow(Long gameTypeId, LocalDateTime currentDateTime);

    // @Query("select gs from GameSlot gs where gs.gameType.id = :gameTypeId and
    // gs.slotEnd>:currentDateTime")
    // List<GameSlot> findAllByGameSlotAfterByEndTime(Long gameTypeId, LocalDateTime
    // currentDateTime);

    GameSlot findBySlotStartAndGameType_Name(LocalDateTime slotStart, String gameTypeName);

    @Query("select gs from GameSlot gs where gs.gameType = :gameType and gs.slotStart>=:startDateTime order by gs.slotStart")
    List<GameSlot> findAllByGameSlotAfter(GameType gameType, LocalDateTime startDateTime);

    @Query("select gs from GameSlot gs where gs.gameType = :gameType and gs.slotEnd>=:startDateTime order by gs.slotStart")
    List<GameSlot> findAllByGameSlotAfterByEndTime(GameType gameType, LocalDateTime startDateTime);
}