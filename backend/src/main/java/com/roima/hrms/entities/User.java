package com.roima.hrms.entities;

import com.roima.hrms.enums.RoleEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users", schema = "office")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_user_id", nullable = false)
    private Long id;

    @Size(max = 255)
    @Column(name = "email")
    private String email;

    @Size(max = 255)
    @Column(name = "password_hash")
    private String passwordHash;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_profile_id", unique = true)
    private EmployeeProfile employeeProfile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;


    @CreationTimestamp
    @Column(name = "created_at")
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "last_login_at")
    private Instant lastLoginAt;


}