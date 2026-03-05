package com.roima.hrms.dtos.req;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TravelEmployeeReqDto {
    @NotNull(message = "Employee id required!")
    private Long id;
}
