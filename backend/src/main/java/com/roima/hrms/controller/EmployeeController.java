package com.roima.hrms.controller;

import java.util.List;
import java.util.Set;

import com.roima.hrms.repository.EmployeeProfileRepository;
import com.roima.hrms.repository.TravelEmployeeRepository;
import com.roima.hrms.response.ApiResponse;
import com.roima.hrms.services.EmployeeService;
import com.roima.hrms.services.UserService;

import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.roima.hrms.dtos.res.EmployeeProfileDto;
import com.roima.hrms.dtos.res.FullEmployeeProfileDto;
import com.roima.hrms.dtos.res.FullEmployeeProfileRoleDto;
import com.roima.hrms.entities.EmployeeProfile;
import com.roima.hrms.entities.TravelEmployee;
import com.roima.hrms.enums.ApiResponseType;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

        private final ModelMapper modelMapper;

        private final EmployeeProfileRepository employeeProfileRepository;
        private final EmployeeService employeeService;

        @GetMapping
        public ResponseEntity<ApiResponse> getEmployees() {
                List<EmployeeProfileDto> dtos = employeeService.getAllEmployees();
                return ResponseEntity.ok(ApiResponse.createApiResponse(
                                ApiResponseType.SUCCESS, "Employess fetched", dtos, null));

        }

        @GetMapping("travel/{id}")
        public ResponseEntity<ApiResponse> getEmployeesTravelPlan(@PathVariable Long id) {
                List<EmployeeProfileDto> dtos = employeeService.getTravelEmployeesTravelPlan(id);
                return ResponseEntity.ok(ApiResponse.createApiResponse(
                                ApiResponseType.SUCCESS, "Employess fetched", dtos, null));
        }

        @GetMapping("all-details")
        public ResponseEntity<ApiResponse> getFullEmployees() {
                List<FullEmployeeProfileRoleDto> dtos = employeeProfileRepository.findAll().stream()
                                .map((employee) -> modelMapper.map(employee, FullEmployeeProfileRoleDto.class))
                                .toList();
                return ResponseEntity.ok(ApiResponse.createApiResponse(ApiResponseType.SUCCESS, "Employees Fetched",
                                dtos, null));
        }

        @PutMapping("role/{empId}")
        public ResponseEntity<ApiResponse> updateEmployeeRole(@PathVariable Long empId, @RequestBody Long roleId) {
                employeeService.updateEmployeeRole(empId, roleId);
                return ResponseEntity.ok(ApiResponse.createApiResponse(ApiResponseType.SUCCESS, "Role",
                                true, null));
        }
}
