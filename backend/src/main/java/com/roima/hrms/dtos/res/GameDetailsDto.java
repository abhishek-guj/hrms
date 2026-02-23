package com.roima.hrms.dtos.res;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GameDetailsDto {
    private Long gameTypeId;
    private String gameTypeName;
    private Integer maxSlotDurationMinutes;
    private List<Integer> slotSizes;
    private String startTime;
    private String endTime;
}
