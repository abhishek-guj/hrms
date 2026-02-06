package com.roima.hrms.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.time.Instant;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "game_types", schema = "game")
public class GameType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_game_type_id", nullable = false)
    private Long id;

    @Size(max = 255)
    @Nationalized
    @Column(name = "name")
    private String name;

    @Column(name = "max_players")
    private Integer maxPlayers;

    @Column(name = "min_players")
    private Integer minPlayers;

    @Column(name = "max_slot_duration")
    private LocalTime maxSlotDuration;

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