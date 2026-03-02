package com.roima.hrms.services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.roima.hrms.dtos.req.EmployeeInterestsReqDto;
import com.roima.hrms.dtos.res.EmployeeProfileDto;
import com.roima.hrms.dtos.res.FullEmployeeProfileDto;
import com.roima.hrms.entities.EmployeeInterest;
import com.roima.hrms.entities.EmployeeProfile;
import com.roima.hrms.entities.GameType;
import com.roima.hrms.entities.User;
import com.roima.hrms.repository.EmployeeProfileRepository;
import com.roima.hrms.repository.GameTypeRepository;
import com.roima.hrms.utils.RoleUtil;

@Service
public class ProfileService {

    private final EmployeeProfileRepository employeeProfileRepository;
    private final GameTypeRepository gameTypeRepository;
    private final ModelMapper modelMapper;
    private final RoleUtil roleUtil;

    public ProfileService(
            EmployeeProfileRepository employeeProfileRepository,
            GameTypeRepository gameTypeRepository,
            ModelMapper modelMapper,
            RoleUtil roleUtil

    ) {

        this.employeeProfileRepository = employeeProfileRepository;
        this.gameTypeRepository = gameTypeRepository;
        this.modelMapper = modelMapper;
        this.roleUtil = roleUtil;
    }

    public FullEmployeeProfileDto getEmployeeProfile() {
        EmployeeProfile profile = roleUtil.getCurrentEmployee();

        FullEmployeeProfileDto profileDto = modelMapper.map(profile, FullEmployeeProfileDto.class);
        String userEmail = profile.getUser().getEmail();

        EmployeeProfile manager = profile.getManager();
        profileDto.setUserEmail(userEmail);
        if (manager != null && manager.getUser() != null) {
            profileDto.setManager(modelMapper.map(manager, EmployeeProfileDto.class));
        } else {
            profileDto.setManager(null);
        }

        return profileDto;
    }

    public FullEmployeeProfileDto updateEmployeeProfile(EmployeeInterestsReqDto interestsDto) {
        EmployeeProfile existingProfile = roleUtil.getCurrentEmployee();

        existingProfile.getEmployeeInterests().clear();

        List<Long> gameTypeIds = interestsDto.getGameTypeIds();
        gameTypeIds.forEach(gameTypeId -> {
            EmployeeInterest employeeInterest = new EmployeeInterest();

            GameType gameType = gameTypeRepository.findById(gameTypeId)
                    .orElseThrow(
                            () -> new RuntimeException("GameType not found with id: " + gameTypeId));
                            
            employeeInterest.setEmployeeProfile(existingProfile);
            employeeInterest.setGameType(gameType);
            existingProfile.getEmployeeInterests().add(employeeInterest);
        });

        employeeProfileRepository.save(existingProfile);
        return modelMapper.map(existingProfile, FullEmployeeProfileDto.class);
    }
}
