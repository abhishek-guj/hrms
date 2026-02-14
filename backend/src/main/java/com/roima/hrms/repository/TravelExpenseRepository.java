package com.roima.hrms.repository;

import com.roima.hrms.dtos.res.TravelExpenseDto;
import com.roima.hrms.entities.TravelExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TravelExpenseRepository extends JpaRepository<TravelExpense, Long> {

    @Query(value = "select te from TravelExpense te where te.id = :id")
    Optional<TravelExpense> findById(Long id);

    @Override
    @Query(value = "SELECT te,tp From TravelExpense te join fetch te.travelPlan tp")
    List<TravelExpense> findAll();
}