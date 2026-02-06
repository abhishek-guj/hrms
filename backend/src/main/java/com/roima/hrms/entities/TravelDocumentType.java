package com.roima.hrms.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

@Getter
@Setter
@Entity
@Table(name = "travel_document_types", schema = "travel")
public class TravelDocumentType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_travel_document_type_id", nullable = false)
    private Long id;

    @Size(max = 255)
    @Nationalized
    @Column(name = "name")
    private String name;


}