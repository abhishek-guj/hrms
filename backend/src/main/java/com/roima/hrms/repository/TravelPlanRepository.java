package com.roima.hrms.repository;

import com.roima.hrms.entities.TravelEmployee;
import com.roima.hrms.entities.TravelPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface TravelPlanRepository extends JpaRepository<TravelPlan, Long> {
    // find by employee
    @Query("select distinct tp from TravelPlan tp join tp.travelEmployees te where te.employeeProfile.id = :employeeId order by tp.id desc")
    List<TravelPlan> findByEmployeeId(Long employeeId);
    // find by manager
    @Query("select distinct tp from TravelPlan tp join tp.travelEmployees te join te.employeeProfile ep where ep.manager.id = :managerId order by tp.id desc")
    List<TravelPlan> findByManagerId(Long managerId);

    @Override
    @Query("select distinct tp from TravelPlan tp order by tp.id desc")
    List<TravelPlan> findAll();
    // find by status

    // validate the new dates dont conflict with current dates
}