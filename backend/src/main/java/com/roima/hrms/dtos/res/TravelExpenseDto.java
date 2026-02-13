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

@Getter
@Setter
public class TravelExpenseDto {
    private Long id;
    private String travelPlanPurpose;
    private String employeeName;
    private Boolean submitStatus;
    private LocalDate expenseUploadDate;
    private String expenseTypeName;
    private BigDecimal expenseAmount;
    private LocalDate expenseDate;
    private String status;
    private String remark;
    private Instant statusChangedOn;
    private Long statusChangedBy;
}
