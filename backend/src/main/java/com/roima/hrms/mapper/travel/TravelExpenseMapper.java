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

    public TravelExpense toEntity(TravelExpenseDto dto){
        return modelMapper.map(dto,TravelExpense.class);
    }

    public TravelExpense toEntity(TravelExpenseRequestDto dto){

        return modelMapper.map(dto,TravelExpense.class);
    }

    public TravelExpenseDto toTravelExpenseDto(TravelExpense travelExpense){
        List<ExpenseDocument> docs = expenseDocumentRepository.findExpenseDocumentByTravelExpense(travelExpense);
        var a = modelMapper.map(travelExpense, TravelExpenseDto.class);
        a.setExpenseDocumentFilePaths(docs.stream().map(ExpenseDocument::getFilePath).toList());
        return a;
    }

//    public TravelExpense updateEntity(TravelExpense travelExpense, TravelExpenseRequestDto){
//        travelExpense.get
//    }

    public List<TravelExpenseDto> toTravelExpenseDtoList(List<TravelExpense> travelExpenseList){
        return travelExpenseList.stream().map(this::toTravelExpenseDto).collect(Collectors.toList());
    }

    public TravelExpense toUpdateEntity(TravelExpense travelExpense, TravelExpenseRequestDto travelExpenseDto){
        travelExpense.setSubmitStatus(travelExpenseDto.getSubmitStatus());
        travelExpense.setExpenseUploadDate(LocalDate.parse(travelExpenseDto.getExpenseUploadDate()));
        travelExpense.setExpenseAmount(travelExpenseDto.getExpenseAmount());
        travelExpense.setExpenseDate(LocalDate.parse(travelExpenseDto.getExpenseDate()));
        travelExpense.setStatus(travelExpenseDto.getStatus());
        travelExpense.setRemark(travelExpenseDto.getRemark());
        return  travelExpense;
    }
}
