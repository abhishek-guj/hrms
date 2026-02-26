package com.roima.hrms.mapper.travel;

import com.roima.hrms.dtos.res.TravelDocumentTypeDto;
import com.roima.hrms.entities.TravelDocumentType;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TravelDocumentTypeMapper {

    private final ModelMapper modelMapper;

    public TravelDocumentTypeMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public TravelDocumentType toEntity(TravelDocumentTypeDto dto) {
        return modelMapper.map(dto, TravelDocumentType.class);
    }

    public TravelDocumentTypeDto toTravelDocumentTypeDto(TravelDocumentType travelDocumentType) {
        return modelMapper.map(travelDocumentType, TravelDocumentTypeDto.class);
    }

    public List<TravelDocumentTypeDto> toTravelDocumentTypeDtoList(List<TravelDocumentType> travelDocumentTypeList) {
        return travelDocumentTypeList.stream().map(this::toTravelDocumentTypeDto).collect(Collectors.toList());
    }
}
