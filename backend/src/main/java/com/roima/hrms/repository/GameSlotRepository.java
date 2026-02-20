package com.roima.hrms.repository;

import com.roima.hrms.entities.GameSlot;
import com.roima.hrms.entities.GameType;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface GameSlotRepository extends JpaRepository<GameSlot, Long> {

    @Query("select gs from GameSlot gs where gs.gameType.id = :gameTypeId order by gs.slotEnd desc limit 1")
    Optional<GameSlot> findFirstByGameTypeIdOrderBySlotEndDesc(Long gameTypeId);

    boolean existsByGameType(GameType gameType);
}