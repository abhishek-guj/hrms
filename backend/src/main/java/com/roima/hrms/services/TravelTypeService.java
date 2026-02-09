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
        TravelType t = TravelType.builder().name(dto.getName()).build();
        travelTypeRepository.save(t);
        return travelTypeMapper.toTravelTypeDto(t);
    }

    public TravelTypeDto getById(Long id){
        TravelType t = travelTypeRepository.findById(id).orElseThrow(()->new TravelTypeNotFoundException());
        return travelTypeMapper.toTravelTypeDto(t);
    }

    public void deleteTravelType(Long id){
        travelTypeRepository.deleteById(id);
        return;

    }
    public TravelTypeDto updateTravelType(Long id, TravelTypeRequestDto dto){
        TravelType t = travelTypeRepository.findById(id).orElseThrow(()-> new TravelTypeNotFoundException());
        t.setName(dto.getName());
        return travelTypeMapper.toTravelTypeDto(t);
    }
}
