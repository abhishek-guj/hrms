package com.roima.hrms.mapper.travel;

import com.roima.hrms.dtos.res.TravelTypeDto;
import com.roima.hrms.entities.TravelType;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class TravelTypeMapper {

    private final ModelMapper modelMapper;

    public TravelTypeMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public TravelType toEntity(TravelTypeDto dto){
        return modelMapper.map(dto,TravelType.class);
    }

    public TravelTypeDto toTravelTypeDto(TravelType travelType){
        return modelMapper.map(travelType, TravelTypeDto.class);
    }

    public List<TravelTypeDto> toTravelTypeDtoList(List<TravelType> travelTypeList){
        return travelTypeList.stream().map(this::toTravelTypeDto).collect(Collectors.toList());
    }
}
