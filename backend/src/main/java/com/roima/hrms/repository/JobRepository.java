package com.roima.hrms.repository;

import com.roima.hrms.entities.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {
    boolean existsJobByJobTitle(String jobTitle);
}