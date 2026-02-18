package com.roima.hrms.dtos.res;


import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrgEmployeeDto {
    private Long employeeProfileId;
    private String firstName;
    private String lastName;
    private boolean currentEmployee;
    List<OrgEmployeeDto> underEmployees = new ArrayList<>();
}
