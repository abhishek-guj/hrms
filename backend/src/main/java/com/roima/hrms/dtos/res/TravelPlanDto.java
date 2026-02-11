package com.roima.hrms.dtos.res;


import com.roima.hrms.entities.EmployeeProfile;
import com.roima.hrms.entities.TravelType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
public class TravelPlanDto {
    private Long id;
    private String purpose;
    private TravelTypeDto travelType;
    private LocalDate startDate;
    private LocalDate endDate;
    private String destinationCity;
    private String destinationState;
    private String destinationCountry;
    private LocalDate lastDateOfExpenseSubmission;
    private BigDecimal maxAmountPerDay;
//    private Instant createdOn;
//    private Instant updatedOn;
//    private EmployeeProfile createdBy;
//    private EmployeeProfile updatedBy;
//    private EmployeeProfile deletedBy;

}
