package com.roima.hrms.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.Nationalized;

import java.time.Instant;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "game_types", schema = "game")
public class GameType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_game_type_id", nullable = false)
    private Long id;

    @Size(max = 255)
    @Column(name = "name")
    private String name;

    @Column(name = "max_slot_duration_minutes")
    private Integer maxSlotDurationMinutes;

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

    @OneToMany(mappedBy = "gameType", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<GameOperationHour> gameOperationHours = new HashSet<>();

    @OneToMany(mappedBy = "gameType", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<GameSlot> gameSlots;

    @OneToMany(mappedBy = "gameType", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<GameSlotSize> gameSlotSize;

}