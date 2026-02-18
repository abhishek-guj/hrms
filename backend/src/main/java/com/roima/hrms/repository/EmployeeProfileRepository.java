package com.roima.hrms.repository;

import com.roima.hrms.entities.EmployeeProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeProfileRepository extends JpaRepository<EmployeeProfile, Long> {
    Optional<EmployeeProfile> findByFirstName(String firstName);

    boolean existsByFirstName(String firstName);

    EmployeeProfile getEmployeeProfileByUser_Email(String email);

    List<EmployeeProfile> getAllByManager_Id(Long managerId);
}