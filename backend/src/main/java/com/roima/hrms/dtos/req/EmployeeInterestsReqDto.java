package com.roima.hrms.dtos.req;

import java.util.List;

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
public class EmployeeInterestsReqDto {
    @NotNull(message = "game ids cannot be null")
    private List<Long> gameTypeIds;
}