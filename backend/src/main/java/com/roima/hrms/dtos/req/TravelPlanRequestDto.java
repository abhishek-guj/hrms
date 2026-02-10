package com.roima.hrms.dtos.req;


import com.roima.hrms.entities.EmployeeProfile;
import com.roima.hrms.entities.TravelType;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
public class TravelPlanRequestDto {
    private String name;
    private Long travelTypeId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String destinationCity;
    private String destinationState;
    private String destinationCountry;
    private LocalDate lastDateOfExpenseSubmission;
    private BigDecimal maxAmountPerDay;
}
