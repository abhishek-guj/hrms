package com.roima.hrms.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.Nationalized;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "travel_documents", schema = "travel")
public class TravelDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_travel_document_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="travel_plan_id", nullable = false)
    private TravelPlan travelPlan;

    @Column(name = "owner_type", nullable = false)
    private String ownerType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uploaded_by_employee",nullable = false)
    private EmployeeProfile uploadedByEmployee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uploaded_for_employee",nullable = false)
    private EmployeeProfile uploadedForEmployee;

    @Column(name = "upload_date")
    private LocalDateTime uploadDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_type_id")
    private TravelDocumentType documentType;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "deleted_by")
    private Long deletedBy;

    @Column(name = "is_deleted")
    private Boolean isDeleted;


}