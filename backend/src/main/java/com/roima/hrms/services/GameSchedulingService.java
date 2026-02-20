package com.roima.hrms.services;

import com.roima.hrms.entities.EmployeeProfile;
import com.roima.hrms.entities.PlayerGroup;
import com.roima.hrms.exceptions.EmployeeNotFoundException;
import com.roima.hrms.repository.EmployeeProfileRepository;
import com.roima.hrms.utils.RoleUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameSchedulingService {


    private final RoleUtil roleUtil;
    private final EmployeeProfileRepository employeeProfileRepository;

    public GameSchedulingService(RoleUtil roleUtil, EmployeeProfileRepository employeeProfileRepository) {
        this.roleUtil = roleUtil;
        this.employeeProfileRepository = employeeProfileRepository;
    }

    public void createPlayerGroup(List<Long> playerIds){
        EmployeeProfile currentPlayer = roleUtil.getCurrentEmployee();

        PlayerGroup playerGroup = PlayerGroup.builder().players(List.of(currentPlayer)).build();

        playerIds.forEach(id->{
            EmployeeProfile player = employeeProfileRepository.findById(id).orElseThrow(EmployeeNotFoundException::new);
            playerGroup.getPlayers().add(player);
        });
    }
}
