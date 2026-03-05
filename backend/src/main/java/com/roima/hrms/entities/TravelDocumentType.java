package com.roima.hrms.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.Nationalized;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "travel_document_types", schema = "travel")
public class TravelDocumentType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_travel_document_type_id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    // @Builder.Default
    @Column(name = "is_deleted")
    private Boolean isDeleted = false;
}