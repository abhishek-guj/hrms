package com.roima.hrms.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

@Getter
@Setter
@Entity
@Table(name = "expense_types", schema = "travel")
public class ExpenseType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_expense_type_id", nullable = false)
    private Long id;

    @Size(max = 255)
    @Nationalized
    @Column(name = "expense_type")
    private String expenseType;


}