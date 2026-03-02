package com.roima.hrms.controller;

import java.util.List;
import java.util.Set;

import com.roima.hrms.repository.EmployeeProfileRepository;
import com.roima.hrms.repository.TravelEmployeeRepository;
import com.roima.hrms.response.ApiResponse;
import com.roima.hrms.services.EmployeeService;

import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.roima.hrms.dtos.res.EmployeeProfileDto;
import com.roima.hrms.entities.EmployeeProfile;
import com.roima.hrms.entities.TravelEmployee;
import com.roima.hrms.enums.ApiResponseType;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

        private final ModelMapper modelMapper;

        private final EmployeeProfileRepository employeeProfileRepository;
        private final TravelEmployeeRepository travelEmployeeRepository;
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

        // @GetMapping("game-slot/{slotId}")
        // public ResponseEntity<ApiResponse> getEmployeesGameSlot(@PathVariable Long
        // slotId) {
        // List<EmployeeProfileDto> dtos =
        // employeeService.getPlayersForGameSlot(slotId);
        // return ResponseEntity.ok(ApiResponse.createApiResponse(
        // ApiResponseType.SUCCESS, "Employess fetched", dtos, null));
        // }
}
