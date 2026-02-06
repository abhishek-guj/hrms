package com.roima.hrms.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "player_groups", schema = "game")
public class PlayerGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_slot_gourp_id", nullable = false)
    private Long id;

    @Column(name = "employee_id")
    private Long employeeId;


}