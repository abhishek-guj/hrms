package com.roima.hrms.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "game_queues", schema = "game")
public class GameQueue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_queue_id", nullable = false)
    private Long id;

    @Column(name = "slot_id")
    private Long slotId;

    @Column(name = "group_id")
    private Long groupId;


}