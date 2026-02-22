package com.roima.hrms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.roima.hrms.entities.GameSlotSize;
import com.roima.hrms.entities.GameType;

import java.util.List;
import java.util.Set;

@Repository
public interface GameSlotSizeRepository extends JpaRepository<GameSlotSize, Long> {

    @Query("select gss from GameSlotSize gss where gss.gameType = :gameTypeId and gss.slotSize = :slotSize ")
    boolean existsByGameTypeAndSlotSize(Long gameTypeId, int slotSize);

    @Query("select gss from GameSlotSize gss where gss.gameType = :gameType order by gss.slotSize")
    Set<GameSlotSize> findAllByGameTypeOrderBySlotSize(GameType gameType);

}
