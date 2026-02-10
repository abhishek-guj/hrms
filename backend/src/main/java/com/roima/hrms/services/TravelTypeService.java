package com.roima.hrms.services;

import com.roima.hrms.dtos.req.TravelTypeRequestDto;
import com.roima.hrms.dtos.res.TravelTypeDto;
import com.roima.hrms.entities.TravelType;
import com.roima.hrms.exceptions.TravelTypeNotFoundException;
import com.roima.hrms.mapper.TravelTypeMapper;
import com.roima.hrms.repository.TravelTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TravelTypeService {
    private final TravelTypeRepository travelTypeRepository;
    private final TravelTypeMapper travelTypeMapper;

    @Autowired
    public TravelTypeService(TravelTypeRepository travelTypeRepository, TravelTypeMapper travelTypeMapper) {
        this.travelTypeRepository = travelTypeRepository;
        this.travelTypeMapper = travelTypeMapper;
    }

    public List<TravelTypeDto> getAllTravelTypes(){
        List<TravelType> travelTypes = travelTypeRepository.findAll();
        return travelTypeMapper.travelTypeDtoList(travelTypes);
    }

    public TravelTypeDto createTravelType(TravelTypeRequestDto dto){
        TravelType tt = TravelType.builder().name(dto.getName()).build();
        travelTypeRepository.save(tt);
        return travelTypeMapper.toTravelTypeDto(tt);
    }

    public TravelTypeDto getById(Long id){
        TravelType tt = travelTypeRepository.findById(id).orElseThrow(TravelTypeNotFoundException::new);
        return travelTypeMapper.toTravelTypeDto(tt);
    }

    public void deleteTravelType(Long id){
        TravelType tt = travelTypeRepository.findById(id).orElseThrow(TravelTypeNotFoundException::new);
        travelTypeRepository.delete(tt);
    }
    public TravelTypeDto updateTravelType(Long id, TravelTypeRequestDto dto){
        TravelType tt = travelTypeRepository.findById(id).orElseThrow(TravelTypeNotFoundException::new);
        tt.setName(dto.getName());
        travelTypeRepository.save(tt);
        return travelTypeMapper.toTravelTypeDto(tt);
    }
}
