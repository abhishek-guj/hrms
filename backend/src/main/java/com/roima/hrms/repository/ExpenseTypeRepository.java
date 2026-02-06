package com.roima.hrms.repository;

import com.roima.hrms.entities.ExpenseType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseTypeRepository extends JpaRepository<ExpenseType, Long> {
}