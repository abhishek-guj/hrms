package com.roima.hrms.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "job_hrs", schema = "job")
public class JobHr {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_job_hr_id", nullable = false)
    private Long id;

    @Column(name = "job_id")
    private Long jobId;

    @Column(name = "hr_id")
    private Long hrId;


}