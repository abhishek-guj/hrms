package com.roima.hrms.repository;

import com.roima.hrms.entities.GameType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameTypeRepository extends JpaRepository<GameType, Long> {
}