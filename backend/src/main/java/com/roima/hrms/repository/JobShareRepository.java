package com.roima.hrms.repository;

import com.roima.hrms.entities.JobHr;
import com.roima.hrms.entities.JobShare;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JobShareRepository extends JpaRepository<JobShare, Long> {

    @Query("""
            select js
            from JobShare js
            order by
            js.sharedOn desc,
            js.sharedBy.id
            """)
    List<JobShare> findAllOrderByDesc();

}