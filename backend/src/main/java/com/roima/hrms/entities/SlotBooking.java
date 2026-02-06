package com.roima.hrms.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

@Getter
@Setter
@Entity
@Table(name = "slot_bookings", schema = "game")
public class SlotBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_slot_booking_id", nullable = false)
    private Long id;

    @Column(name = "player_group_id")
    private Long playerGroupId;

    @Column(name = "slot_id")
    private Long slotId;

    @Size(max = 255)
    @NotNull
    @Nationalized
    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "owner_id")
    private Long ownerId;


}