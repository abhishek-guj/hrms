package com.roima.hrms.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.Nationalized;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "travel_expense", schema = "travel")
public class TravelExpense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_travel_expense_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_plan_id", nullable = false, foreignKey = @ForeignKey(name = "fk_travel_plan_expense_id"))
    private TravelPlan travelPlan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_porfile_id", nullable = false)
    private EmployeeProfile submittedBy;

    @Column(name = "submit_status")
    private Boolean submitStatus;

    @Column(name = "expense_upload_date")
    private LocalDate expenseUploadDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "expense_type_id", nullable = false)
    private ExpenseType expenseType;

    @Column(name = "expense_amount", precision = 18)
    private BigDecimal expenseAmount;

    @Column(name = "expense_date")
    private LocalDate expenseDate;

    @Size(max = 255)
    @NotNull
    @Column(name = "status", nullable = false)
    private String status;

    @Size(max = 255)
    @Column(name = "remark")
    private String remark;

    @Column(name = "status_changed_on")
    private LocalDateTime statusChangedOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_changed_by_id", nullable = false)
    private EmployeeProfile statusChangedBy;

    @OneToMany(mappedBy = "travelExpense", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ExpenseDocument> expenseDocuments = new HashSet<>();
}