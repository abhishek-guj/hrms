package com.roima.hrms.repository;

import com.roima.hrms.entities.TravelDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TravelDocumentRepository extends JpaRepository<TravelDocument, Long> {
    List<TravelDocument> getTravelDocumentsByUploadedForEmployee_IdAndTravelPlan_Id(Long uploadedForEmployeeId, Long travelPlanId);


//    @Query(value = "select te from TravelExpense te join te.submittedBy ep where ep.manager.id = :managerId")
    @Query("select td from TravelDocument td join td.uploadedForEmployee ep where ep.manager.id = :managerId and td.travelPlan.id = :travelPlanId")
    List<TravelDocument> getTravelDocumentsByManagerId(Long managerId, Long travelPlanId);

    List<TravelDocument> getTravelDocumentsByTravelPlan_Id(Long travelPlanId);


    @Query("select distinct td from TravelDocument td join EmployeeProfile ep where ep.manager.id = :managerId and td.id = :travelDocumentId")
    TravelDocument getTravelDocumentByManagerId(Long currentEmployeeId, Long travelDocumentId);

    @Query("select distinct td from TravelDocument td join TravelEmployee te on te.travelPlan.id = td.travelPlan.id where te.employeeProfile.id = :employeeId and td.id = :id")
    TravelDocument getTravelDocumentByIdForEmployee(Long id, Long employeeId);

    TravelDocument getTravelDocumentById(Long id);
}
