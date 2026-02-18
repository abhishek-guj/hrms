package com.roima.hrms.dtos.res;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeProfileDto {
    private Long id;
    private String firstName;
    private String lastName;
//    private String fullName; // testing modelMapper add mappping functionality
}
