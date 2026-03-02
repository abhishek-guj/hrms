package com.roima.hrms.services;

import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.roima.hrms.dtos.res.EmployeeProfileDto;
import com.roima.hrms.entities.EmployeeProfile;
import com.roima.hrms.entities.GameSlot;
import com.roima.hrms.repository.EmployeeProfileRepository;
import com.roima.hrms.repository.GameSlotRepository;
import com.roima.hrms.repository.TravelEmployeeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeService {

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

    // public List<EmployeeProfileDto> getPlayersForGameSlot(Long slotId) {
    // GameSlot gameSlot = gameSlotRepository.findById(slotId)
    // .orElseThrow(() -> new RuntimeException("Game Slot not found"));
    // List<EmployeeProfile> players =
    // employeeProfileRepository.findAllNotOverlapingGameSlots(slotId,
    // gameSlot.getSlotStart(), gameSlot.getSlotEnd());

    // List<EmployeeProfileDto> dtos = players.stream().map(e -> modelMapper.map(e,
    // EmployeeProfileDto.class))
    // .toList();
    // return dtos;
    // }

}
