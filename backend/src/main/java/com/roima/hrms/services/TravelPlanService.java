package com.roima.hrms.services;

import com.roima.hrms.dtos.req.TravelPlanRequestDto;
import com.roima.hrms.dtos.res.TravelPlanDto;
import com.roima.hrms.entities.TravelPlan;
import com.roima.hrms.entities.TravelType;
import com.roima.hrms.exceptions.travel.TravelPlanNotFoundException;
import com.roima.hrms.exceptions.travel.TravelTypeNotFoundException;
import com.roima.hrms.mapper.travel.TravelPlanMapper;
import com.roima.hrms.mapper.travel.TravelTypeMapper;
import com.roima.hrms.repository.TravelPlanRepository;
import com.roima.hrms.repository.TravelTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TravelPlanService {
    private final TravelPlanRepository travelPlanRepository;
    private final TravelPlanMapper travelPlanMapper;
    private final TravelTypeRepository travelTypeRepository;
    private final TravelTypeMapper travelTypeMapper;

    @Autowired
    public TravelPlanService(TravelPlanRepository travelPlanRepository, TravelPlanMapper travelPlanMapper, TravelTypeRepository travelTypeRepository, TravelTypeMapper travelTypeMapper) {
        this.travelPlanRepository = travelPlanRepository;
        this.travelPlanMapper = travelPlanMapper;
        this.travelTypeRepository = travelTypeRepository;
        this.travelTypeMapper = travelTypeMapper;
    }

    public List<TravelPlanDto> getAllTravelPlans() {
        List<TravelPlan> travelPlans = travelPlanRepository.findAll();
        return travelPlanMapper.toTravelPlanDtoList(travelPlans);
    }
    public TravelPlanDto getById(Long id) {
        TravelPlan tp = travelPlanRepository.findById(id).orElseThrow(TravelPlanNotFoundException::new);
        return travelPlanMapper.toTravelPlanDto(tp);
    }

    public TravelPlanDto createTravelPlan(TravelPlanRequestDto dto) {

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

        if(!travelPlanRepository.existsById(id)){
            throw new TravelPlanNotFoundException();
        }
        if(!travelTypeRepository.existsById(dto.getTravelTypeId())){
            throw new TravelTypeNotFoundException();
        }

        // setting values of travelplan dto to travelplan enitity directly
        TravelPlan tp = travelPlanMapper.toEntity(dto);
        TravelType travelType = travelTypeRepository.findById(dto.getTravelTypeId()).orElseThrow(TravelTypeNotFoundException::new);

        // reset id to asked id
        tp.setId(id);
        // reset travel type in case: it was updated
        tp.setTravelType(travelType);

        travelPlanRepository.save(tp);
        return travelPlanMapper.toTravelPlanDto(tp);
    }

    public void deleteTravelPlan(Long id) {
        TravelPlan tp = travelPlanRepository.findById(id).orElseThrow(TravelPlanNotFoundException::new);
        travelPlanRepository.delete(tp);
    }

}
