package com.roima.hrms.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "game_slots", schema = "game")
public class GameSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_game_slot_id", nullable = false)
    private Long id;

    @Column(name = "game_type_id")
    private Long gameTypeId;

    @Column(name = "slot_start")
    private Instant slotStart;

    @Column(name = "slot_end")
    private Instant slotEnd;


}