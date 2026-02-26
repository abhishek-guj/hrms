package com.roima.hrms.repository;

import com.roima.hrms.entities.TravelEmployee;
import com.roima.hrms.entities.TravelPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TravelPlanRepository extends JpaRepository<TravelPlan, Long> {

    @Override
    @Query("select tp from TravelPlan tp where tp.id = :travelPlanId and tp.isDeleted = false ")
    Optional<TravelPlan> findById(Long travelPlanId);

    // find by employee
    @Query("select distinct tp from TravelPlan tp join tp.travelEmployees te where te.employeeProfile.id = :employeeId and tp.isDeleted = false order by tp.id desc")
    List<TravelPlan> findByEmployeeId(Long employeeId);

    // find by manager
    @Query("""
                select distinct
                tp
                from TravelPlan tp
                join tp.travelEmployees te
                join te.employeeProfile ep
                where (ep.manager.id = :managerId or ep.id = :managerId)
                and tp.isDeleted = false
                order by
                tp.id desc
            """)
    List<TravelPlan> findByManagerId(Long managerId);

    @Override
    @Query("select distinct tp from TravelPlan tp where tp.isDeleted = false order by tp.id desc")
    List<TravelPlan> findAll();
    // find by status

    @Override
    @Modifying
    @Query("update TravelPlan tp set tp.isDeleted = true where tp.id = :travelPlanId")
    void deleteById(Long travelPlanId);
    // validate the new dates dont conflict with current dates
}