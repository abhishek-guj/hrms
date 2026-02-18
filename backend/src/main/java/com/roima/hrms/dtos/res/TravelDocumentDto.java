package com.roima.hrms.dtos.res;

import com.roima.hrms.entities.EmployeeProfile;
import com.roima.hrms.entities.TravelDocumentType;
import com.roima.hrms.entities.TravelPlan;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TravelDocumentDto {

    private Long id;

    //    private TravelDocumentTravelPlanDto travelPlan;
    private Long travelPlanId;
    private String travelPlanPurpose;

    private String ownerType;

    //    private TravelDocumentEmployeeProfileDto uploadedByEmployee;
    private Long uploadedById;
    private String uploadedByName;

    //    private TravelDocumentEmployeeProfileDto uploadedForEmployee;
    private Long uploadedForId;
    private String uploadedForName;

    private LocalDateTime uploadDate;

//     private TravelDocumentTypeDto documentType;
    private Long documentTypeId;
    private String documentTypeName;

    private String filePath;
}


@Getter
@Setter
class TravelDocumentTravelPlanDto {
    private Long id;
    private String purpose;
}

@Getter
@Setter
class TravelDocumentEmployeeProfileDto {
    private Long id;
    private String firstName;
    private String lastName;
}

