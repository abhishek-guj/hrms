package com.roima.hrms.dtos.req;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class TravelExpenseRequestDto {
    private String name;
    private Long expenseTypeId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String destinationCity;
    private String destinationState;
    private String destinationCountry;
    private LocalDate lastDateOfExpenseSubmission;
    private BigDecimal maxAmountPerDay;
}
