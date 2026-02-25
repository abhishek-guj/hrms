package com.roima.hrms.repository;

import com.roima.hrms.entities.Job;

import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JobRepository extends JpaRepository<Job, Long> {
    boolean existsJobByJobTitle(String jobTitle);

    @Override
    @Query("select j from Job j where j.id=:id and j.isDeleted = false")
    Optional<Job> findById(Long id);

    @Override
    @Query("select j from Job j where j.isDeleted = false")
    List<Job> findAll();
}