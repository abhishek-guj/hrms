package com.roima.hrms.services;

import com.roima.hrms.dtos.req.TravelDocumentTypeRequestDto;
import com.roima.hrms.dtos.res.TravelDocumentTypeDto;
import com.roima.hrms.entities.TravelDocumentType;
import com.roima.hrms.exceptions.travel.TravelDocumentTypeNotFoundException;
import com.roima.hrms.mapper.travel.TravelDocumentTypeMapper;
import com.roima.hrms.repository.TravelDocumentTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TravelDocumentTypeService {
    private final TravelDocumentTypeRepository travelDocumentTypeRepository;
    private final TravelDocumentTypeMapper travelDocumentTypeMapper;

    @Autowired
    public TravelDocumentTypeService(TravelDocumentTypeRepository travelDocumentTypeRepository, TravelDocumentTypeMapper travelDocumentTypeMapper) {
        this.travelDocumentTypeRepository = travelDocumentTypeRepository;
        this.travelDocumentTypeMapper = travelDocumentTypeMapper;
    }

    public List<TravelDocumentTypeDto> getAllTravelDocumentTypes(){
        List<TravelDocumentType> travelDocumentTypes = travelDocumentTypeRepository.findAll();
        return travelDocumentTypeMapper.toTravelDocumentTypeDtoList(travelDocumentTypes);
    }

    public TravelDocumentTypeDto createTravelDocumentType(TravelDocumentTypeRequestDto dto){
        TravelDocumentType et = TravelDocumentType.builder().name(dto.getName()).build();
        travelDocumentTypeRepository.save(et);
        return travelDocumentTypeMapper.toTravelDocumentTypeDto(et);
    }

    public TravelDocumentTypeDto getById(Long id){
        TravelDocumentType et = travelDocumentTypeRepository.findById(id).orElseThrow(TravelDocumentTypeNotFoundException::new);
        return travelDocumentTypeMapper.toTravelDocumentTypeDto(et);
    }

    public void deleteTravelDocumentType(Long id){
        TravelDocumentType et = travelDocumentTypeRepository.findById(id).orElseThrow(TravelDocumentTypeNotFoundException::new);
        travelDocumentTypeRepository.delete(et);
    }
    public TravelDocumentTypeDto updateTravelDocumentType(Long id, TravelDocumentTypeRequestDto dto){
        TravelDocumentType et = travelDocumentTypeRepository.findById(id).orElseThrow(TravelDocumentTypeNotFoundException::new);
        et.setName(dto.getName());
        travelDocumentTypeRepository.save(et);
        return travelDocumentTypeMapper.toTravelDocumentTypeDto(et);
    }
}
