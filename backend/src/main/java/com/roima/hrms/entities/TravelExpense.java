package com.roima.hrms.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "travel_expense", schema = "travel")
public class TravelExpense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_travel_expense_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_plan_id", nullable = false, foreignKey = @ForeignKey(name = "fk_travel_plan_expense_id"))
    private TravelPlan travelPlan;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_porfile_id", nullable = false)
    private EmployeeProfile employeeProfile;

    @Column(name = "submit_status")
    private Boolean submitStatus;

    @Column(name = "expense_upload_date")
    private Instant expenseUploadDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "expense_type_id", nullable = false)
    private ExpenseType expenseType;

    @Column(name = "expense_amount", precision = 18)
    private BigDecimal expenseAmount;

    @Column(name = "expense_date")
    private Instant expenseDate;

    @Size(max = 255)
    @NotNull
    @Column(name = "status", nullable = false)
    private String status;

    @Size(max = 255)
    @Column(name = "remark")
    private String remark;

    @Column(name = "status_changed_on")
    private Instant statusChangedOn;

    @Column(name = "status_changed_by")
    private Long statusChangedBy;


}