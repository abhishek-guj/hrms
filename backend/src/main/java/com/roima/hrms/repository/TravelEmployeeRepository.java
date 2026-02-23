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
    @Query("select te from TravelEmployee te Join fetch te.employeeProfile where te.travelPlan.id = :travelPlanId and te.travelPlan.isDeleted = false")
    List<TravelEmployee> findByTravelPlan_Id(Long travelPlanId);

    @Query("select (count(t) > 0) from TravelEmployee t where t.travelPlan = :travelPlan and t.employeeProfile = :employeeProfile and t.travelPlan.isDeleted = false")
    boolean existsByTravelPlanAndEmployeeProfile(TravelPlan travelPlan, EmployeeProfile employeeProfile);

    @Modifying
    @Query("delete from TravelEmployee te where te.travelPlan.id = :travelPlanId and te.employeeProfile.id = :employeeId")
    void removeTravelEmployeesByTravelPlan_IdAndEmployeeProfile_Id(Long travelPlanId, Long employeeId);

    void deleteAllByTravelPlan_Id(Long travelPlanId);

    @Query("select ep from TravelEmployee te join fetch EmployeeProfile ep on te.employeeProfile = ep  where te.travelPlan.id = :travelPlanId and te.travelPlan.isDeleted = false")
    Set<EmployeeProfile> getAllEmployeeProfilesByTravelPlan_Id(Long travelPlanId);

    @Query("select t from TravelEmployee t where t.travelPlan.id = :travelPlanId and t.travelPlan.isDeleted = false")
    Set<TravelEmployee> findAllByTravelPlan_Id(Long travelPlanId);
}