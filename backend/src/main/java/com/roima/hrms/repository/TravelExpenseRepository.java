package com.roima.hrms.repository;

import com.roima.hrms.entities.TravelExpense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelExpenseRepository extends JpaRepository<TravelExpense, Long> {
}