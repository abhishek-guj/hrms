package com.roima.hrms.repository;

import com.roima.hrms.entities.GameType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GameTypeRepository extends JpaRepository<GameType, Long> {
    Optional<GameType> findByName(String name);

    boolean existsByName(String name);
}