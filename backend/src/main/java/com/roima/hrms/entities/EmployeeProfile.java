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
@Table(name = "employee_profile", schema = "office")
public class EmployeeProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_employee_id", nullable = false)
    private Long id;

    @Column(name = "fk_user_id")
    private Long fkUserId;

    @Column(name = "fk_role_id")
    private Long fkRoleId;

    @Column(name = "department_id")
    private Long departmentId;

    @Column(name = "fk_manager_id")
    private Long fkManagerId;

    @Size(max = 255)
    @NotNull
    @Nationalized
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Size(max = 255)
    @NotNull
    @Nationalized
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "contact_number")
    private Integer contactNumber;

    @Column(name = "joining_date")
    private LocalDate joiningDate;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "updated_on")
    private Instant updatedOn;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "deleted_by")
    private Long deletedBy;

    @Column(name = "is_deleted")
    private Boolean isDeleted;


}