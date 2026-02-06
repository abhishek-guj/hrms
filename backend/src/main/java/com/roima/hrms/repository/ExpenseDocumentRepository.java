package com.roima.hrms.repository;

import com.roima.hrms.entities.ExpenseDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseDocumentRepository extends JpaRepository<ExpenseDocument, Long> {
}