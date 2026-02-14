package com.roima.hrms.mapper.travel;

import com.roima.hrms.dtos.req.TravelExpenseRequestDto;
import com.roima.hrms.dtos.res.TravelExpenseDto;
import com.roima.hrms.entities.TravelExpense;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class TravelExpenseMapper {

    private final ModelMapper modelMapper;

    public TravelExpenseMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public TravelExpense toEntity(TravelExpenseDto dto){
        return modelMapper.map(dto,TravelExpense.class);
    }

    public TravelExpense toEntity(TravelExpenseRequestDto dto){

        return modelMapper.map(dto,TravelExpense.class);
    }

    public TravelExpenseDto toTravelExpenseDto(TravelExpense travelExpense){
        var a = modelMapper.map(travelExpense, TravelExpenseDto.class);
        return a;
    }

//    public TravelExpense updateEntity(TravelExpense travelExpense, TravelExpenseRequestDto){
//        travelExpense.get
//    }

    public List<TravelExpenseDto> toTravelExpenseDtoList(List<TravelExpense> travelExpenseList){
        return travelExpenseList.stream().map(this::toTravelExpenseDto).collect(Collectors.toList());
    }
}
