package com.roima.hrms.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "travel_documents", schema = "travel")
public class TravelDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_travel_document_id", nullable = false)
    private Long id;

    @Column(name = "travel_plan_id")
    private Long travelPlanId;

    @Column(name = "owner_type")
    private Long ownerType;

    @Column(name = "uploaded_by_employee")
    private Long uploadedByEmployee;

    @Column(name = "upload_date")
    private Instant uploadDate;

    @Column(name = "uploaded_for_employee")
    private Long uploadedForEmployee;

    @Column(name = "document_type_id")
    private Long documentTypeId;

    @Size(max = 255)
    @Nationalized
    @Column(name = "document_name")
    private String documentName;

    @Size(max = 255)
    @Nationalized
    @Column(name = "file_path")
    private String filePath;

    @Size(max = 255)
    @Nationalized
    @Column(name = "file_type")
    private String fileType;

    @Column(name = "deleted_by")
    private Long deletedBy;

    @Column(name = "is_deleted")
    private Boolean isDeleted;


}