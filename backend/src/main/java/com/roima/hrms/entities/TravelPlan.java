package com.roima.hrms.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "travel_plans", schema = "travel")
public class TravelPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_travel_plan_id", nullable = false)
    private Long id;

    @Column(name = "fk_travel_type")
    private Long fkTravelType;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Size(max = 255)
    @Nationalized
    @Column(name = "destination_city")
    private String destinationCity;

    @Size(max = 255)
    @Nationalized
    @Column(name = "destination_state")
    private String destinationState;

    @Size(max = 255)
    @Nationalized
    @Column(name = "destination_country")
    private String destinationCountry;

    @Column(name = "last_date_of_expense_submission")
    private Instant lastDateOfExpenseSubmission;

    @Column(name = "max_amount_per_day", precision = 18)
    private BigDecimal maxAmountPerDay;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "updated_on")
    private Instant updatedOn;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "deleted_by")
    private Long deletedBy;

    @Column(name = "is_deleted")
    private Boolean isDeleted;


}