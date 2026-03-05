package com.roima.hrms.services;

import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.roima.hrms.dtos.res.EmployeeProfileDto;
import com.roima.hrms.entities.EmployeeProfile;
import com.roima.hrms.entities.GameSlot;
import com.roima.hrms.exceptions.EmployeeNotFoundException;
import com.roima.hrms.repository.EmployeeProfileRepository;
import com.roima.hrms.repository.GameSlotRepository;
import com.roima.hrms.repository.TravelEmployeeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final UserService userService;

    private final TravelEmployeeRepository travelEmployeeRepository;
    private final EmployeeProfileRepository employeeProfileRepository;
    private final GameSlotRepository gameSlotRepository;
    private final ModelMapper modelMapper;

    public List<EmployeeProfileDto> getTravelEmployeesTravelPlan(Long id) {
        Set<EmployeeProfile> profiles = travelEmployeeRepository.getAllEmployeeProfilesByTravelPlan_Id(id);
        List<EmployeeProfileDto> dtos = profiles.stream().map(e -> modelMapper.map(e, EmployeeProfileDto.class))
                .toList();
        return dtos;
    }

    public List<EmployeeProfileDto> getAllEmployees() {
        List<EmployeeProfile> profiles = employeeProfileRepository.findAll();
        List<EmployeeProfileDto> dtos = profiles.stream().map(e -> modelMapper.map(e, EmployeeProfileDto.class))
                .toList();
        return dtos;
    }

    public EmployeeProfile getEmployeeProfileById(Long employeeProfileId) {
        return employeeProfileRepository.findById(employeeProfileId).orElseThrow(EmployeeNotFoundException::new);
    }

    public void updateEmployeeRole(Long empId, Long roleId) {
        EmployeeProfile employeeProfile = employeeProfileRepository.findById(empId)
                .orElseThrow(EmployeeNotFoundException::new);
        userService.updateUserRole(employeeProfile, roleId);
    }
}
