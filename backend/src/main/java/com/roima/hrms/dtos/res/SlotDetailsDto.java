package com.roima.hrms.dtos.res;

import java.util.List;

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
    private List<Integer> slotSizes;
    private boolean inGroup;
    // queue in waiting list
}
