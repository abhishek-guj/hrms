package com.roima.hrms.repository;

import com.roima.hrms.entities.TravelPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TravelPlanRepository extends JpaRepository<TravelPlan, Long> {
}