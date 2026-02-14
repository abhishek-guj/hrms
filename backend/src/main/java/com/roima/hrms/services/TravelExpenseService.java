package com.roima.hrms.services;

import com.roima.hrms.dtos.req.TravelExpenseRequestDto;
import com.roima.hrms.dtos.res.TravelExpenseDto;
import com.roima.hrms.entities.TravelExpense;
import com.roima.hrms.entities.ExpenseType;
import com.roima.hrms.exceptions.travel.ExpenseTypeNotFoundException;
import com.roima.hrms.mapper.travel.TravelExpenseMapper;
import com.roima.hrms.mapper.travel.ExpenseTypeMapper;
import com.roima.hrms.repository.TravelExpenseRepository;
import com.roima.hrms.repository.ExpenseTypeRepository;
import com.roima.hrms.utils.RoleUtil;
import org.springdoc.core.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TravelExpenseService {
    private final TravelExpenseRepository travelExpenseRepository;
    private final TravelExpenseMapper travelExpenseMapper;
    private final ExpenseTypeRepository expenseTypeRepository;
    private final ExpenseTypeMapper expenseTypeMapper;
    private final RoleUtil roleUtil;

    @Autowired
    public TravelExpenseService(TravelExpenseRepository travelExpenseRepository, TravelExpenseMapper travelExpenseMapper, ExpenseTypeRepository expenseTypeRepository, ExpenseTypeMapper expenseTypeMapper, SecurityService securityService, RoleUtil roleUtil) {
        this.travelExpenseRepository = travelExpenseRepository;
        this.travelExpenseMapper = travelExpenseMapper;
        this.expenseTypeRepository = expenseTypeRepository;
        this.expenseTypeMapper = expenseTypeMapper;
        this.roleUtil = roleUtil;
    }

    public List<TravelExpenseDto> getAllTravelExpenses() {

        String role = roleUtil.getRole();
        if(role)
        List<TravelExpense> travelExpenses = travelExpenseRepository.findAll();
        return travelExpenseMapper.toTravelExpenseDtoList(travelExpenses);
    }

    public TravelExpenseDto getById(Long id) {
        TravelExpense travelExpense = travelExpenseRepository.findById(id).orElseThrow(() -> new RuntimeException("Travel Expense Not Found!"));
        return travelExpenseMapper.toTravelExpenseDto(travelExpense);
    }

    public TravelExpenseDto createTravelExpense(TravelExpenseRequestDto dto) {

        // converting to entity
        TravelExpense travelExpense = travelExpenseMapper.toEntity(dto);

        // saving
        travelExpenseRepository.save(travelExpense);

        // returning
        return travelExpenseMapper.toTravelExpenseDto(travelExpense);
    }


    public TravelExpenseDto updateTravelExpense(Long id, TravelExpenseRequestDto dto) {

        if (!travelExpenseRepository.existsById(id)) {
            throw new RuntimeException("Travel Expense Not Found!");
        }
        if (!expenseTypeRepository.existsById(dto.getExpenseTypeId())) {
            throw new ExpenseTypeNotFoundException();
        }

        // setting values of travelExpense dto to travelExpense enitity directly
        TravelExpense travelExpense = travelExpenseMapper.toEntity(dto);
        ExpenseType expenseType = expenseTypeRepository.findById(dto.getExpenseTypeId()).orElseThrow(ExpenseTypeNotFoundException::new);

        // reset id to asked id
        travelExpense.setId(id);
        // reset travel type in case: it was updated
        travelExpense.setExpenseType(expenseType);

        travelExpenseRepository.save(travelExpense);
        return travelExpenseMapper.toTravelExpenseDto(travelExpense);
    }

    public void deleteTravelExpense(Long id) {
        TravelExpense travelExpense = travelExpenseRepository.findById(id).orElseThrow(() -> new RuntimeException("Travel Expense Not Found!"));
        travelExpenseRepository.delete(travelExpense);
    }

}
