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
@Table(name = "job_referrals", schema = "job")
public class JobReferral {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_job_referral_id", nullable = false)
    private Long id;

    @Column(name = "job_id")
    private Long jobId;

    @Size(max = 255)
    @Nationalized
    @Column(name = "fname")
    private String fname;

    @Size(max = 255)
    @Nationalized
    @Column(name = "lname")
    private String lname;

    @Size(max = 255)
    @Nationalized
    @Column(name = "email")
    private String email;

    @Column(name = "contact_num")
    private Integer contactNum;

    @Column(name = "referred_on")
    private Instant referredOn;

    @Column(name = "reffered_by")
    private Long refferedBy;

    @Size(max = 255)
    @Nationalized
    @Column(name = "cv_path")
    private String cvPath;

    @Size(max = 255)
    @Nationalized
    @Column(name = "note")
    private String note;

    @Size(max = 255)
    @NotNull
    @Nationalized
    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "status_changed_on")
    private Instant statusChangedOn;


}