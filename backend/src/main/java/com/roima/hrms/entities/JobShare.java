package com.roima.hrms.entities;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "job_shares", schema = "job")
public class JobShare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_job_share_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id")
    private Job job;

    @Column(name = "shared_on")
    private LocalDateTime sharedOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shared_by")
    private EmployeeProfile sharedBy;

    @Column(name="is_email_sent")
    private boolean isEmailSent;
}
