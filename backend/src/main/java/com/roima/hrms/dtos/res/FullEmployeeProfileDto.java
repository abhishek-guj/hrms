package com.roima.hrms.dtos.res;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FullEmployeeProfileDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String userEmail;
    private Integer contactNumber;
    private EmployeeProfileDto manager;
    private LocalDate birthDate;
    private LocalDate joiningDate;
    private List<EmployeeInterestDto> interests;
}