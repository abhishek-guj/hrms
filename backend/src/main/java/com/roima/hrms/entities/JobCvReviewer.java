package com.roima.hrms.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "job_cv_reviewers", schema = "job")
public class JobCvReviewer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_job_cv_reviewer_id", nullable = false)
    private Long id;

    @Column(name = "job_id")
    private Long jobId;

    @Column(name = "reviewer_id")
    private Long reviewerId;


}