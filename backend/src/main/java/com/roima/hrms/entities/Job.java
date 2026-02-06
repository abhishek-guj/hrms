package com.roima.hrms.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "jobs", schema = "job")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_job_id", nullable = false)
    private Long id;

    @Size(max = 255)
    @Nationalized
    @Column(name = "job_title")
    private String jobTitle;

    @Size(max = 255)
    @Nationalized
    @Column(name = "job_details")
    private String jobDetails;

    @Column(name = "experience_level")
    private Integer experienceLevel;

    @Column(name = "number_of_vaccancy")
    private Integer numberOfVaccancy;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "last_update_date")
    private Instant lastUpdateDate;

    @Column(name = "last_updated_by")
    private Long lastUpdatedBy;

    @Column(name = "updated_on")
    private Instant updatedOn;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "deleted_by")
    private Long deletedBy;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Size(max = 255)
    @NotNull
    @Nationalized
    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "status_changed_on")
    private Instant statusChangedOn;


}