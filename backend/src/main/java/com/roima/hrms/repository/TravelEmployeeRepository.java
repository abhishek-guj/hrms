package com.roima.hrms.repository;

import com.roima.hrms.entities.TravelEmployee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelEmployeeRepository extends JpaRepository<TravelEmployee, Long> {
}