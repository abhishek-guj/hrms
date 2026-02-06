package com.roima.hrms.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "job_jd_files", schema = "job")
public class JobJdFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_jobs_jd_file_id", nullable = false)
    private Long id;

    @Column(name = "job_id")
    private Long jobId;

    @Size(max = 255)
    @Nationalized
    @Column(name = "file_path")
    private String filePath;

    @Size(max = 255)
    @Nationalized
    @Column(name = "file_name")
    private String fileName;

    @Column(name = "uploaded_on")
    private Instant uploadedOn;

    @Column(name = "uploaded_by")
    private Long uploadedBy;


}