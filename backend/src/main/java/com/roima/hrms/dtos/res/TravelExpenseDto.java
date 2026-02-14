package com.roima.hrms.dtos.res;


import com.roima.hrms.entities.EmployeeProfile;
import com.roima.hrms.entities.ExpenseType;
import com.roima.hrms.entities.TravelPlan;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class TravelExpenseDto {
    private Long id;
    private ExpenseTravelPlanDto travelPlan;
    private ExpenseEmployeeProfileDto employee;
    private Boolean submitStatus;
    private LocalDate expenseUploadDate;
    private ExpenseExpenseTypeDto expenseType;
    private BigDecimal expenseAmount;
    private LocalDate expenseDate;
    private String status;
    private String remark;
    private LocalDateTime statusChangedOn;
    private ExpenseEmployeeProfileDto statusChangedBy;
}

@Getter
@Setter
class ExpenseTravelPlanDto {
    private Long id;
    private String purpose;
}

@Getter
@Setter
class ExpenseEmployeeProfileDto {
    private Long id;
    private String firstName;
    private String lastName;
}

@Getter
@Setter
class ExpenseExpenseTypeDto{
    private Long id;
    private String name;
}