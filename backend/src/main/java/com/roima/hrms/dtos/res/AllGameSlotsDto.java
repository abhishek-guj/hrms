package com.roima.hrms.dtos.res;


import com.roima.hrms.entities.GameSlot;
import com.roima.hrms.entities.GameType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AllGameSlotsDto {

    // gameTypeId = list of slots
    private Long gameTypeId;
    private String gameTypeName;
    private List<GameSlotDto> gameSlots;

}

