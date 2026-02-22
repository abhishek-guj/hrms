package com.roima.hrms.dtos.res;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GameSlotDto{
    private Long id;
    private Long gameTypeId;
    private String gameTypeName;
    private LocalDateTime slotStart;
    private LocalDateTime slotEnd;
    private boolean isBooked;
    private boolean isLowPriority;
}

