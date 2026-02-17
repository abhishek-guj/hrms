package com.roima.hrms.dtos.res;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class TravelExpenseDto {
    private Long id;
    private ExpenseTravelPlanDto travelPlan;
    private ExpenseEmployeeProfileDto submittedBy;
    private Boolean submitStatus;
    private LocalDate expenseUploadDate;
    private ExpenseExpenseTypeDto expenseType;
    private BigDecimal expenseAmount;
    private LocalDate expenseDate;
    private String status;
    private String remark;
    private LocalDateTime statusChangedOn;
    private ExpenseEmployeeProfileDto statusChangedBy;
    private List<String> expenseDocumentFilePaths;
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