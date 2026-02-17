package com.roima.hrms.repository;

import com.roima.hrms.entities.ExpenseDocument;
import com.roima.hrms.entities.TravelExpense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseDocumentRepository extends JpaRepository<ExpenseDocument, Long> {
    List<ExpenseDocument> findExpenseDocumentByTravelExpense(TravelExpense travelExpense);
}