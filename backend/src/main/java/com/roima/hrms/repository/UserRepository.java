package com.roima.hrms.repository;

import com.roima.hrms.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}