package com.roima.hrms.repository;

import com.roima.hrms.entities.Role;
import com.roima.hrms.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRole(RoleEnum role);
    boolean existsByRole(RoleEnum role);
}