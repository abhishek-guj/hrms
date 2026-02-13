package com.roima.hrms.controller.travel;


import com.roima.hrms.dtos.req.ExpenseTypeRequestDto;
import com.roima.hrms.dtos.res.ExpenseTypeDto;
import com.roima.hrms.enums.ApiResponseType;
import com.roima.hrms.response.ApiResponse;
import com.roima.hrms.services.ExpenseTypeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/expense-types")
@Tag(name="Expense Type")
public class ExpenseTypeController {

    private final ExpenseTypeService expenseTypeService;

    public ExpenseTypeController(ExpenseTypeService expenseTypeService) {
        this.expenseTypeService = expenseTypeService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllExpenseTypes(){
        List<ExpenseTypeDto> expenseTypeDtoList = expenseTypeService.getAllExpenseTypes();
        ApiResponse<List<ExpenseTypeDto>> res = ApiResponse.createApiResponse(ApiResponseType.SUCCESS,"Fetched all Expense types successfully", expenseTypeDtoList,null);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createExpenseType(@RequestBody ExpenseTypeRequestDto dto){
        ExpenseTypeDto tt = expenseTypeService.createExpenseType(dto);
        ApiResponse<ExpenseTypeDto> res = ApiResponse.createApiResponse(ApiResponseType.SUCCESS,"Successfully created Expense type.", tt,null);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse> getExpenseType(@PathVariable Long id){
        ExpenseTypeDto tt = expenseTypeService.getById(id);
        ApiResponse<ExpenseTypeDto> res = ApiResponse.createApiResponse(ApiResponseType.SUCCESS,"Successfully fetched expense type", tt,null);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PutMapping("{id}")
    public ResponseEntity<ApiResponse> updateExpenseType(@PathVariable Long id, @RequestBody ExpenseTypeRequestDto dto){
        ExpenseTypeDto expenseType = expenseTypeService.updateExpenseType(id, dto);
        ApiResponse<ExpenseTypeDto> res = ApiResponse.createApiResponse(ApiResponseType.SUCCESS,"Successfully updated expense type", expenseType,null);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse> deleteExpenseType(@PathVariable Long id){
        expenseTypeService.deleteExpenseType(id);
        ApiResponse<Void> res = ApiResponse.createApiResponse(ApiResponseType.SUCCESS,"Successfully deleted expense type", null,null);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }
}
