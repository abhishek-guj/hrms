package com.roima.hrms.dtos.res;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrgEmployeeDto {
    private Long employeeProfileId;
    private String firstName;
    private Long managerId;
}
