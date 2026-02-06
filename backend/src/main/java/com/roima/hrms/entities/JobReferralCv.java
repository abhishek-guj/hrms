package com.roima.hrms.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "job_referral_cvs", schema = "job")
public class JobReferralCv {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_job_referral_cv_id", nullable = false)
    private Long id;

    @Column(name = "job_id")
    private Long jobId;

    @Column(name = "referral_id")
    private Long referralId;


}