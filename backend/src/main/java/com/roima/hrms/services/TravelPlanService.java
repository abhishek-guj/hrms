package com.roima.hrms.services;

import com.roima.hrms.dtos.req.TravelPlanRequestDto;
import com.roima.hrms.dtos.res.TravelEmployeeDto;
import com.roima.hrms.dtos.res.TravelPlanDto;
import com.roima.hrms.entities.TravelEmployee;
import com.roima.hrms.entities.TravelPlan;
import com.roima.hrms.entities.TravelType;
import com.roima.hrms.exceptions.UnauthorizedException;
import com.roima.hrms.exceptions.travel.TravelPlanNotFoundException;
import com.roima.hrms.exceptions.travel.TravelTypeNotFoundException;
import com.roima.hrms.mapper.travel.TravelPlanMapper;
import com.roima.hrms.mapper.travel.TravelTypeMapper;
import com.roima.hrms.repository.TravelEmployeeRepository;
import com.roima.hrms.repository.TravelPlanRepository;
import com.roima.hrms.repository.TravelTypeRepository;
import com.roima.hrms.utils.RoleUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TravelPlanService {
    private final TravelPlanRepository travelPlanRepository;
    private final TravelPlanMapper travelPlanMapper;
    private final TravelTypeRepository travelTypeRepository;
    private final TravelTypeMapper travelTypeMapper;
    private final ModelMapper modelMapper;
    private final RoleUtil roleUtil;

    @Autowired
    public TravelPlanService(TravelPlanRepository travelPlanRepository, TravelPlanMapper travelPlanMapper, TravelTypeRepository travelTypeRepository, TravelTypeMapper travelTypeMapper, ModelMapper modelMapper, RoleUtil roleUtil) {
        this.travelPlanRepository = travelPlanRepository;
        this.travelPlanMapper = travelPlanMapper;
        this.travelTypeRepository = travelTypeRepository;
        this.travelTypeMapper = travelTypeMapper;

        this.modelMapper = modelMapper;
        this.roleUtil = roleUtil;
    }

    public List<TravelPlanDto> getAllTravelPlans() {
        // getting user role
        String role = roleUtil.getRole();
        if (role == null) {
            throw new UnauthorizedException("User not authenticated");
        }


        // getting employee profile
        List<TravelPlan> travelPlans;


        if (roleUtil.isEmployee()) {
            var employeeId = roleUtil.getCurrentEmployeeId();
            travelPlans = travelPlanRepository.findByEmployeeId(employeeId);
        } else if (roleUtil.isManager()) {
            travelPlans = travelPlanRepository.findByManagerId(roleUtil.getCurrentEmployeeId());
        } else {
            // if hr or admin
            travelPlans = travelPlanRepository.findAll();
        }

        return travelPlanMapper.toTravelPlanDtoList(travelPlans);
    }

    public TravelPlanDto getById(Long id) {
        TravelPlan tp = travelPlanRepository.findById(id).orElseThrow(TravelPlanNotFoundException::new);
        return travelPlanMapper.toTravelPlanDto(tp);
    }



    public TravelPlanDto createTravelPlan(TravelPlanRequestDto dto) {

        if(!roleUtil.isAdmin()||!roleUtil.isHr()){
            throw new RuntimeException("Not Authorised to create");
        }

        // getting travel type
        TravelType travelType = travelTypeRepository.findById(dto.getTravelTypeId()).orElseThrow(TravelTypeNotFoundException::new);

        // converting to entity
        TravelPlan tp = travelPlanMapper.toEntity(dto);

        // setting type to entity
        tp.setTravelType(travelType);

        // saving
        travelPlanRepository.save(tp);

        // returning
        return travelPlanMapper.toTravelPlanDto(tp);
    }


    public TravelPlanDto updateTravelPlan(Long id, TravelPlanRequestDto dto) {

        if(!roleUtil.isAdmin()||!roleUtil.isHr()){
            throw new RuntimeException("Not Authorised to create");
        }

        TravelPlan travelPlan = travelPlanRepository.findById(id).orElseThrow(TravelPlanNotFoundException::new);

        TravelType travelType = travelTypeRepository.findById(dto.getTravelTypeId()).orElseThrow(TravelTypeNotFoundException::new);

        // setting values of travelplan dto to travelplan enitity directly
        travelPlan.setTravelType(travelType);

        TravelPlan updatedEntity = travelPlanMapper.updateEntity(travelPlan, dto);

        travelPlanRepository.save(updatedEntity);

        return travelPlanMapper.toTravelPlanDto(updatedEntity);
    }

    public void deleteTravelPlan(Long id) {
        TravelPlan tp = travelPlanRepository.findById(id).orElseThrow(TravelPlanNotFoundException::new);
        travelPlanRepository.deleteById(tp.getId());
    }



}
