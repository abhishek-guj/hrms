package com.roima.hrms.repository;

import com.roima.hrms.entities.EmployeeProfile;
import com.roima.hrms.entities.Job;
import com.roima.hrms.entities.JobHr;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface JobHrRepository extends JpaRepository<JobHr, Long> {
    Set<JobHr> findAllByJob(Job job);
}