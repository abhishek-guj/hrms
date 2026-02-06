package com.roima.hrms.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "expense_documents", schema = "travel")
public class ExpenseDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_expense_document_id", nullable = false)
    private Long id;

    @Column(name = "travel_expense_id")
    private Long travelExpenseId;

    @Size(max = 255)
    @Nationalized
    @Column(name = "file_path")
    private String filePath;

    @Size(max = 255)
    @Nationalized
    @Column(name = "file_type")
    private String fileType;

    @Column(name = "uploaded_by")
    private Long uploadedBy;

    @Column(name = "uploaded_at")
    private Instant uploadedAt;


}