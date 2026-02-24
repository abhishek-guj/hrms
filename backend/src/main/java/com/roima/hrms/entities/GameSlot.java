package com.roima.hrms.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "game_slots", schema = "game")
public class GameSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_game_slot_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_type_id")
    private GameType gameType;

    @Column(name = "slot_start")
    private LocalDateTime slotStart;

    @Column(name = "slot_end")
    private LocalDateTime slotEnd;

    @OneToMany(mappedBy = "slot", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<GameQueue> gameQueues;

    @OneToMany(mappedBy = "gameSlot", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SlotBooking> slotBookings;
}