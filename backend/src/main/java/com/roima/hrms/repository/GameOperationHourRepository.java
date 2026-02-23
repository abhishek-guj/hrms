package com.roima.hrms.repository;

import com.roima.hrms.entities.GameOperationHour;
import com.roima.hrms.entities.GameType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameOperationHourRepository extends JpaRepository<GameOperationHour, Long> {
    boolean existsByGameType(GameType gameType);

    GameOperationHour findFirstByGameTypeOrderByStartTime(GameType gameType);

    List<GameOperationHour> findAllByGameType(GameType gameType);
}