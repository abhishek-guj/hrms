package com.roima.hrms.controller;

import java.util.List;
import com.roima.hrms.repository.EmployeeProfileRepository;
import com.roima.hrms.response.ApiResponse;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.roima.hrms.dtos.res.EmployeeProfileDto;
import com.roima.hrms.entities.EmployeeProfile;
import com.roima.hrms.enums.ApiResponseType;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    private final ModelMapper modelMapper;

    private final EmployeeProfileRepository employeeProfileRepository;

    EmployeeController(EmployeeProfileRepository employeeProfileRepository, ModelMapper modelMapper) {
        this.employeeProfileRepository = employeeProfileRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getEmployees() {
        List<EmployeeProfile> profiles = employeeProfileRepository.findAll();
        List<EmployeeProfileDto> dtos = profiles.stream().map(e -> modelMapper.map(e, EmployeeProfileDto.class))
                .toList();
        return ResponseEntity.ok(ApiResponse.createApiResponse(
                ApiResponseType.SUCCESS, "Employess feed fetched", dtos, null));

    }
}
