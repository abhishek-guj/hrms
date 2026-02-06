package com.roima.hrms.repository;

import com.roima.hrms.entities.GameQueue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameQueueRepository extends JpaRepository<GameQueue, Long> {
}