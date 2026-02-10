package com.roima.hrms.repository;

import com.roima.hrms.entities.TravelType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TravelTypeRepository extends JpaRepository<TravelType, Long> {
    Optional<TravelType> findByName(String name);
}