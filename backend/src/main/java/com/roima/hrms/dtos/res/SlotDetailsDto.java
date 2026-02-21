package com.roima.hrms.dtos.res;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SlotDetailsDto {
    private GameSlotDto gameSlot;
    private boolean canBook;
    // max players in group
    // queue in waiting list
}
