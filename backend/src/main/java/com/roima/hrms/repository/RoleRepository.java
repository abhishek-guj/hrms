package com.roima.hrms.repository;

import com.roima.hrms.entities.Role;
import com.roima.hrms.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRole(RoleEnum role);
    boolean existsByRole(RoleEnum role);
}