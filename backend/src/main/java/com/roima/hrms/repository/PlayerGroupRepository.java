package com.roima.hrms.repository;

import com.roima.hrms.entities.EmployeeProfile;
import com.roima.hrms.entities.PlayerGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerGroupRepository extends JpaRepository<PlayerGroup, Long> {
    boolean existsByPlayersContains(List<EmployeeProfile> players);
}