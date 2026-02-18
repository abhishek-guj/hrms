package com.roima.hrms.repository;

import com.roima.hrms.entities.EmployeeProfile;
import com.roima.hrms.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmployeeProfileRepository extends JpaRepository<EmployeeProfile, Long> {
    Optional<EmployeeProfile> findByFirstName(String firstName);

    boolean existsByFirstName(String firstName);

    @Query("select ep from EmployeeProfile ep where ep.user.email = :email")
    EmployeeProfile getEmployeeProfileByUser_Email(String email);

    List<EmployeeProfile> getAllByManager_Id(Long managerId);

    @Query("select ep from User ep")
    List<User> getAll();
}