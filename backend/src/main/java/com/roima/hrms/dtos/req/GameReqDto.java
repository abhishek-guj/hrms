package com.roima.hrms.dtos.req;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameReqDto {

    private Long gameTypeId;

    @NotEmpty
    @NotNull(message = "name cant be null")
    private String gameTypeName;

    @NotNull(message = "slot time cant be null")
    private Integer maxSlotDurationMinutes;

    @NotNull(message = "slot sizes cant be null")
    private List<Integer> slotSizes;

    @NotEmpty
    @NotNull(message = "start time cant be null")
    private String startTime;

    @NotEmpty
    @NotNull(message = "end time cant be null")
    private String endTime;
}