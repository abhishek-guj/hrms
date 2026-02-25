package com.roima.hrms.repository;

import com.roima.hrms.entities.JobReferral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JobReferralRepository extends JpaRepository<JobReferral, Long> {
    @Query("select jr from JobReferral jr order by jr.referredOn desc ")
    List<JobReferral> getJobReferralsHr();

    @Query("select jr from JobReferral jr where jr.referredBy.id = :employeeId order by jr.referredOn desc")
    List<JobReferral> getJobReferrals(Long employeeId);
}