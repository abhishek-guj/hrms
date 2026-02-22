package com.roima.hrms.dtos.res;

import java.util.List;

import com.roima.hrms.entities.EmployeeProfile;
import com.roima.hrms.entities.GameSlot;
import com.roima.hrms.entities.PlayerGroup;
import com.roima.hrms.enums.SlotBookingStatusEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SlotBookingDto {
    private Long id;

    private PlayerGroupDto playerGroup;

    private GameSlotDto gameSlot;

    private String status;

    private EmployeeProfileDto groupOwner;

    private List<Integer> slotSizes;

    private boolean isInQueue;

    private long queueSize;
}
