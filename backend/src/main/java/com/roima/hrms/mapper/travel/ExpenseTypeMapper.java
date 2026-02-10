package com.roima.hrms.mapper.travel;

import com.roima.hrms.dtos.res.ExpenseTypeDto;
import com.roima.hrms.entities.ExpenseType;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class ExpenseTypeMapper {

    private final ModelMapper modelMapper;

    public ExpenseTypeMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ExpenseType toEntity(ExpenseTypeDto dto){
        return modelMapper.map(dto,ExpenseType.class);
    }

    public ExpenseTypeDto toExpenseTypeDto(ExpenseType expenseType){
        return modelMapper.map(expenseType, ExpenseTypeDto.class);
    }

    public List<ExpenseTypeDto> expenseTypeDtoList(List<ExpenseType> expenseTypeList){
        return expenseTypeList.stream().map(this::toExpenseTypeDto).collect(Collectors.toList());
    }
}
