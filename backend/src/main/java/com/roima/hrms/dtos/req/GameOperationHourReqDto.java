package com.roima.hrms.dtos.req;

import java.time.LocalTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
public class GameOperationHourReqDto {

    @NotBlank(message = "start time is required")
    @NotEmpty(message = "start time cannot be empty")
    private String startTime;

    @NotEmpty(message = "end time cannot be empty")
    @NotBlank(message = "end time is required")
    private String endTime;
}