package com.roima.hrms.entities;

import com.roima.hrms.enums.RoleEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "roles", schema = "rbac")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_role_id", nullable = false)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING) // specifys to store enum as string not index in db
    @Column(name = "role_name")
    private RoleEnum role;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "update_at")
    private LocalDateTime update_at;
}