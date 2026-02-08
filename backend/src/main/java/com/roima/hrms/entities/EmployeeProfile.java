package com.roima.hrms.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "employee_profiles", schema = "office")
public class EmployeeProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_employee_id", nullable = false)
    private Long id;

    @OneToOne(mappedBy = "employeeProfile", fetch = FetchType.LAZY)
    private User user;

    @Column(name = "department_id", nullable = false)
    private Long departmentId;

    @Column(name = "fk_manager_id", nullable = false)
    private Long fkManagerId;

    @Size(max = 255)
    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Size(max = 255)
    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "contact_number", nullable = false)
    private Integer contactNumber;

    @Column(name = "joining_date", nullable = false)
    private LocalDate joiningDate;

    @Column(name = "created_on", nullable = false)
    private Instant createdOn;

    @Column(name = "updated_on", nullable = false)
    private Instant updatedOn;

    @Column(name = "created_by", nullable = false)
    private Long createdBy;

    @Column(name = "updated_by", nullable = false)
    private Long updatedBy;

    @Column(name = "deleted_by", nullable = false)
    private Long deletedBy;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;


}