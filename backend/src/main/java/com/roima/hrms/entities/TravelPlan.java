package com.roima.hrms.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "travel_plans", schema = "travel")
@SQLDelete(sql = "update travelplan set is_deleted = true, delete_on currenttimestamp where pktrabelplanid = ? ")
public class TravelPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_travel_plan_id", nullable = false)
    private Long id;

    @Size(max = 255)
    @Column(name = "travel_purpose")
    private String purpose;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_type_id", nullable = false)
    private TravelType travelType;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Size(max = 255)
    @Column(name = "destination_city")
    private String destinationCity;

    @Size(max = 255)
    @Column(name = "destination_state")
    private String destinationState;

    @Size(max = 255)
    @Column(name = "destination_country")
    private String destinationCountry;

    @Column(name = "last_date_of_expense_submission")
    private LocalDate lastDateOfExpenseSubmission;

    @Column(name = "max_amount_per_day", precision = 18)
    private BigDecimal maxAmountPerDay;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "updated_on")
    private LocalDateTime updatedOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private EmployeeProfile createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by")
    private EmployeeProfile updatedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deleted_by")
    private EmployeeProfile deletedBy;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    @Builder.Default
    @OneToMany(mappedBy = "travelPlan", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private Set<TravelEmployee> travelEmployees = new HashSet<>();

    // persist
    @PrePersist
    protected void onCreate() {
        createdOn = LocalDateTime.now();
        updatedOn = LocalDateTime.now();
    }

    // on update
    @PreUpdate
    protected void onUpdate() {
        updatedOn = LocalDateTime.now();
    }
}