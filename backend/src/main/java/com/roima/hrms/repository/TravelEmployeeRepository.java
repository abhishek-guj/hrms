package com.roima.hrms.repository;

import com.roima.hrms.entities.EmployeeProfile;
import com.roima.hrms.entities.TravelEmployee;
import com.roima.hrms.entities.TravelPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface TravelEmployeeRepository extends JpaRepository<TravelEmployee, Long> {
    @Query("select te from TravelEmployee te Join fetch te.employeeProfile where te.travelPlan.id = :travelPlanId")
    List<TravelEmployee> findByTravelPlan_Id(Long travelPlanId);

    TravelEmployee findByTravelPlan_IdAndEmployeeProfile_Id(Long travelPlanId, Long employeeProfileId);

    boolean removeTravelEmployeeById(Long travelEmployeeId);

    boolean existsByTravelPlanAndEmployeeProfile(TravelPlan travelPlan, EmployeeProfile employeeProfile);


    @Modifying
    @Query("delete from TravelEmployee te where te.travelPlan.id = :travelPlanId and te.employeeProfile.id = :employeeId")
    void removeTravelEmployeesByTravelPlan_IdAndEmployeeProfile_Id(Long travelPlanId, Long employeeId);

    Set<TravelEmployee> removeTravelEmployeesByTravelPlan_Id(Long travelPlanId);

    void deleteAllByTravelPlan_Id(Long travelPlanId);
}