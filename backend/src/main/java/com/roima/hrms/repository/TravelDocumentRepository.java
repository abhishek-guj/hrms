package com.roima.hrms.repository;

import com.roima.hrms.entities.TravelDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TravelDocumentRepository extends JpaRepository<TravelDocument, Long> {
    List<TravelDocument> getTravelDocumentsByUploadedForEmployee_IdAndTravelPlan_Id(Long uploadedForEmployeeId, Long travelPlanId);


//    @Query(value = "select te from TravelExpense te join te.submittedBy ep where ep.manager.id = :managerId")
    @Query("select td from TravelDocument td join td.uploadedForEmployee ep where ep.manager.id = :managerId")
    List<TravelDocument> getTravelDocumentsByManagerId(Long managerId);

    List<TravelDocument> getTravelDocumentsByTravelPlan_Id(Long travelPlanId);
}