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
@Table(name = "users", schema = "office")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_user_id", nullable = false)
    private Long id;

    @Size(max = 255)
    @Nationalized
    @Column(name = "email")
    private String email;

    @Size(max = 255)
    @Nationalized
    @Column(name = "password_hash")
    private String passwordHash;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "last_login_at")
    private Instant lastLoginAt;


}