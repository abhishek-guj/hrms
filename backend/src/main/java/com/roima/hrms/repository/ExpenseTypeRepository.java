package com.roima.hrms.repository;

import com.roima.hrms.entities.ExpenseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ExpenseTypeRepository extends JpaRepository<ExpenseType, Long> {
    boolean existsByName(String name);

    @Query("update ExpenseType e set e.isDeleted = true where e.id = :id")
    boolean softDeleteById(Long id);
}