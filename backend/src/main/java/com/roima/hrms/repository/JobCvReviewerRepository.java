package com.roima.hrms.repository;

import com.roima.hrms.entities.Job;
import com.roima.hrms.entities.JobCvReviewer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface JobCvReviewerRepository extends JpaRepository<JobCvReviewer, Long> {

    Set<JobCvReviewer> findAllByJob(Job job);
}