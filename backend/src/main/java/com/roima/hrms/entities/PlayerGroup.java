package com.roima.hrms.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "player_groups", schema = "game")
public class PlayerGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_slot_gourp_id", nullable = false)
    private Long id;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "employee_player_group", joinColumns = @JoinColumn(name = "player_groups_id"), inverseJoinColumns = @JoinColumn(name = "pk_employee_id"))
    private List<EmployeeProfile> players;

}