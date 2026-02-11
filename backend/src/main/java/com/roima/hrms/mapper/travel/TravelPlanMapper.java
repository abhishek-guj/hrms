package com.roima.hrms.mapper.travel;

import com.roima.hrms.dtos.req.TravelPlanRequestDto;
import com.roima.hrms.dtos.res.TravelPlanDto;
import com.roima.hrms.entities.TravelPlan;
import com.roima.hrms.entities.TravelType;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class TravelPlanMapper {

    private final ModelMapper modelMapper;

    public TravelPlanMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public TravelPlan toEntity(TravelPlanDto dto){
        return modelMapper.map(dto,TravelPlan.class);
    }

    public TravelPlan toEntity(TravelPlanRequestDto dto){

        return modelMapper.map(dto,TravelPlan.class);
    }

    public TravelPlan updateEntity(TravelPlan travelPlan, TravelPlanRequestDto travelPlanDto){
        travelPlan.setPurpose(travelPlanDto.getPurpose());
        travelPlan.setStartDate(travelPlanDto.getStartDate());
        travelPlan.setEndDate(travelPlanDto.getEndDate());
        travelPlan.setDestinationCity(travelPlanDto.getDestinationCity());
        travelPlan.setDestinationState(travelPlanDto.getDestinationState());
        travelPlan.setDestinationCountry(travelPlanDto.getDestinationCountry());
        travelPlan.setLastDateOfExpenseSubmission(travelPlanDto.getLastDateOfExpenseSubmission());
        travelPlan.setMaxAmountPerDay(travelPlanDto.getMaxAmountPerDay());
        return travelPlan;
    }

    public TravelPlanDto toTravelPlanDto(TravelPlan travelPlan){
        return modelMapper.map(travelPlan, TravelPlanDto.class);
    }

    public List<TravelPlanDto> toTravelPlanDtoList(List<TravelPlan> travelPlanList){
        return travelPlanList.stream().map(this::toTravelPlanDto).collect(Collectors.toList());
    }
}
