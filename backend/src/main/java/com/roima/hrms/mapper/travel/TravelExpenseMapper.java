package com.roima.hrms.mapper.travel;

import com.roima.hrms.dtos.req.TravelExpenseRequestDto;
import com.roima.hrms.dtos.res.TravelExpenseDto;
import com.roima.hrms.entities.ExpenseDocument;
import com.roima.hrms.entities.TravelExpense;
import com.roima.hrms.repository.ExpenseDocumentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TravelExpenseMapper {

    private final ModelMapper modelMapper;
    private final ExpenseDocumentRepository expenseDocumentRepository;

    public TravelExpenseMapper(ModelMapper modelMapper, ExpenseDocumentRepository expenseDocumentRepository) {
        this.modelMapper = modelMapper;
        this.expenseDocumentRepository = expenseDocumentRepository;
    }

    public TravelExpense toEntity(TravelExpenseDto dto) {
        return modelMapper.map(dto, TravelExpense.class);
    }

    public TravelExpense toEntity(TravelExpenseRequestDto dto) {

        return modelMapper.map(dto, TravelExpense.class);
    }

    public TravelExpenseDto toTravelExpenseDto(TravelExpense travelExpense) {
        List<ExpenseDocument> docs = expenseDocumentRepository.findExpenseDocumentByTravelExpense(travelExpense);
        var a = modelMapper.map(travelExpense, TravelExpenseDto.class);
        a.setExpenseDocumentFilePaths(docs.stream().map(ExpenseDocument::getFilePath).toList());
        return a;
    }

    public List<TravelExpenseDto> toTravelExpenseDtoList(List<TravelExpense> travelExpenseList) {
        return travelExpenseList.stream().map(this::toTravelExpenseDto).collect(Collectors.toList());
    }

    public TravelExpense toUpdateEntity(TravelExpense travelExpense, TravelExpenseRequestDto travelExpenseDto) {
        travelExpense.setExpenseAmount(travelExpenseDto.getExpenseAmount());
        travelExpense.setExpenseDate(LocalDate.parse(travelExpenseDto.getExpenseDate()));
        return travelExpense;
    }
}
