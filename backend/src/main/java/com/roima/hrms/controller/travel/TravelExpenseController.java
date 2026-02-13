package com.roima.hrms.controller.travel;


import com.roima.hrms.dtos.req.TravelExpenseRequestDto;
import com.roima.hrms.dtos.res.TravelExpenseDto;
import com.roima.hrms.enums.ApiResponseType;
import com.roima.hrms.response.ApiResponse;
import com.roima.hrms.services.TravelExpenseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/travel-expense")
//@Tag(name="Travel Expense")
public class TravelExpenseController {

    private final TravelExpenseService travelExpenseService;

    public TravelExpenseController(TravelExpenseService travelExpenseService) {
        this.travelExpenseService = travelExpenseService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllTravelExpenses(){
        List<TravelExpenseDto> travelExpenseDtoList = travelExpenseService.getAllTravelExpenses();
        ApiResponse<List<TravelExpenseDto>> res = ApiResponse.createApiResponse(ApiResponseType.SUCCESS,"Fetched all Travel Expenses successfully", travelExpenseDtoList,null);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createTravelExpense(@RequestBody TravelExpenseRequestDto dto){
        TravelExpenseDto tt = travelExpenseService.createTravelExpense(dto);
        ApiResponse<TravelExpenseDto> res = ApiResponse.createApiResponse(ApiResponseType.SUCCESS,"Successfully created Travel Expense.", tt,null);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse> getTravelExpense(@PathVariable Long id){
        TravelExpenseDto tt = travelExpenseService.getById(id);
        ApiResponse<TravelExpenseDto> res = ApiResponse.createApiResponse(ApiResponseType.SUCCESS,"Successfully fetched Travel Expense", tt,null);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PutMapping("{id}")
    public ResponseEntity<ApiResponse> updateTravelExpense(@PathVariable Long id, @RequestBody TravelExpenseRequestDto dto){
        TravelExpenseDto travelExpense = travelExpenseService.updateTravelExpense(id, dto);
        ApiResponse<TravelExpenseDto> res = ApiResponse.createApiResponse(ApiResponseType.SUCCESS,"Successfully updated Travel Expense", travelExpense,null);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse> deleteTravelExpense(@PathVariable Long id){
        travelExpenseService.deleteTravelExpense(id);
        ApiResponse<Void> res = ApiResponse.createApiResponse(ApiResponseType.SUCCESS,"Successfully deleted Travel Expense", null,null);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }
}
