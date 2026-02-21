package com.roima.hrms.entities;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "game_queues", schema = "game")
public class GameQueue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_queue_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "slot_id")
    private GameSlot slot;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private PlayerGroup playerGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="booking_req_by")
    private EmployeeProfile owner;

    @Column(name = "requested_on")
    private LocalDateTime requestedOn;

    @Column(name = "priority_at_request")
    private int priorityAtRequest;

}