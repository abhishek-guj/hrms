package com.roima.hrms.entities;

import com.roima.hrms.enums.SlotBookingStatusEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.Nationalized;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "slot_bookings", schema = "game")
public class SlotBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_slot_booking_id", nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_group_id")
    private PlayerGroup playerGroup;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_slot_id", unique = true)
    private GameSlot gameSlot;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private SlotBookingStatusEnum status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private EmployeeProfile groupOwner;
}