package com.roima.hrms.mapper.travel;

import com.roima.hrms.dtos.req.TravelPlanRequestDto;
import com.roima.hrms.dtos.res.TravelPlanDto;
import com.roima.hrms.entities.TravelPlan;
import com.roima.hrms.entities.TravelType;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TravelPlanMapper {

    private final ModelMapper modelMapper;
    private final TravelTypeMapper travelTypeMapper;

    public TravelPlanMapper(ModelMapper modelMapper, TravelTypeMapper travelTypeMapper) {
        this.modelMapper = modelMapper;
        this.travelTypeMapper = travelTypeMapper;
    }

    public TravelPlan toEntity(TravelPlanDto dto) {
        return modelMapper.map(dto, TravelPlan.class);
    }

    public TravelPlan toEntity(TravelPlanRequestDto dto) {

        return TravelPlan.builder()
                .purpose(dto.getPurpose())
                .startDate(dto.getStartDate())
                .endDate(dto.getStartDate())
                .destinationCity(dto.getDestinationCity())
                .destinationState(dto.getDestinationState())
                .destinationCountry(dto.getDestinationCountry())
                .lastDateOfExpenseSubmission(dto.getLastDateOfExpenseSubmission())
                .maxAmountPerDay(dto.getMaxAmountPerDay())
                .createdOn(LocalDateTime.now())
                .updatedOn(LocalDateTime.now())
                .build();
    }

    public TravelPlan updateEntity(TravelPlan travelPlan, TravelPlanRequestDto travelPlanDto) {
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

    public TravelPlanDto toTravelPlanDto(TravelPlan travelPlan) {
        TravelPlanDto travelPlanDto = new TravelPlanDto();

        travelPlanDto.setId(travelPlan.getId());
        travelPlanDto.setPurpose(travelPlan.getPurpose());
        travelPlanDto.setTravelType(travelTypeMapper.toTravelTypeDto(travelPlan.getTravelType()));
        travelPlanDto.setStartDate(travelPlan.getStartDate());
        travelPlanDto.setEndDate(travelPlan.getEndDate());
        travelPlanDto.setDestinationCity(travelPlan.getDestinationCity());
        travelPlanDto.setDestinationState(travelPlan.getDestinationState());
        travelPlanDto.setDestinationCountry(travelPlan.getDestinationCountry());
        travelPlanDto.setLastDateOfExpenseSubmission(travelPlan.getLastDateOfExpenseSubmission());
        travelPlanDto.setMaxAmountPerDay(travelPlan.getMaxAmountPerDay());

        return travelPlanDto;
    }

    public List<TravelPlanDto> toTravelPlanDtoList(List<TravelPlan> travelPlanList) {

        return travelPlanList.stream()
                .map(this::toTravelPlanDto).collect(Collectors.toList());
    }
}
