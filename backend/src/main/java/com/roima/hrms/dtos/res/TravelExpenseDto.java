package com.roima.hrms.dtos.res;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class TravelExpenseDto {
    private Long id;
    private String name;
    private ExpenseTypeDto travelType;
    private LocalDate startDate;
    private LocalDate endDate;
    private String destinationCity;
    private String destinationState;
    private String destinationCountry;
    private LocalDate lastDateOfExpenseSubmission;
    private BigDecimal maxAmountPerDay;
}
