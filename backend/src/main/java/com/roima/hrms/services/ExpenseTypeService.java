package com.roima.hrms.services;

import com.roima.hrms.dtos.req.ExpenseTypeRequestDto;
import com.roima.hrms.dtos.res.ExpenseTypeDto;
import com.roima.hrms.entities.ExpenseType;
import com.roima.hrms.exceptions.travel.ExpenseTypeNotFoundException;
import com.roima.hrms.mapper.travel.ExpenseTypeMapper;
import com.roima.hrms.repository.ExpenseTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseTypeService {
    private final ExpenseTypeRepository expenseTypeRepository;
    private final ExpenseTypeMapper expenseTypeMapper;

    @Autowired
    public ExpenseTypeService(ExpenseTypeRepository expenseTypeRepository, ExpenseTypeMapper expenseTypeMapper) {
        this.expenseTypeRepository = expenseTypeRepository;
        this.expenseTypeMapper = expenseTypeMapper;
    }

    public List<ExpenseTypeDto> getAllExpenseTypes(){
        List<ExpenseType> expenseTypes = expenseTypeRepository.findAll();
        return expenseTypeMapper.toExpenseTypeDtoList(expenseTypes);
    }

    public ExpenseTypeDto createExpenseType(ExpenseTypeRequestDto dto){
        ExpenseType et = ExpenseType.builder().name(dto.getName()).build();
        expenseTypeRepository.save(et);
        return expenseTypeMapper.toExpenseTypeDto(et);
    }

    public ExpenseTypeDto getById(Long id){
        ExpenseType et = expenseTypeRepository.findById(id).orElseThrow(ExpenseTypeNotFoundException::new);
        return expenseTypeMapper.toExpenseTypeDto(et);
    }

    public void deleteExpenseType(Long id){
        ExpenseType et = expenseTypeRepository.findById(id).orElseThrow(ExpenseTypeNotFoundException::new);
        expenseTypeRepository.delete(et);
    }
    public ExpenseTypeDto updateExpenseType(Long id, ExpenseTypeRequestDto dto){
        ExpenseType et = expenseTypeRepository.findById(id).orElseThrow(ExpenseTypeNotFoundException::new);
        et.setName(dto.getName());
        expenseTypeRepository.save(et);
        return expenseTypeMapper.toExpenseTypeDto(et);
    }
}
