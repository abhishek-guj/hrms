package com.roima.hrms.dtos.req;

import java.util.List;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeInterestsReqDto {
    private List<Long> gameTypeIds;
}