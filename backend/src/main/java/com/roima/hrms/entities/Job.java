package com.roima.hrms.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import org.hibernate.annotations.Mutability;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    @Column(name = "job_details")
    private String jobDetails;

    @Column(name = "experience_years")
    private Integer experienceYears;

    @Column(name = "number_of_vaccancy")
    private Integer numberOfVaccancy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private EmployeeProfile createdBy;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by")
    private EmployeeProfile updatedBy;

    @Column(name = "updated_on")
    private LocalDateTime updatedOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deleted_by")
    private EmployeeProfile deletedBy;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Size(max = 255)
    @NotNull
    @Column(name = "status", nullable = false)
    private String status; // active or close

    @Column(name = "status_changed_on")
    private LocalDateTime statusChangedOn;

    // mapppings
    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<JobHr> jobHrs = new HashSet<>();

    @OneToOne(mappedBy = "job", cascade = CascadeType.ALL, orphanRemoval = true)
    private JobJdFile jobJdFile;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<JobReferral> jobReferrals = new HashSet<>();

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<JobCvReviewer> jobCvReviewers = new HashSet<>();

    public void addJdFile(JobJdFile file) {
        file.setJob(this);
    }

}