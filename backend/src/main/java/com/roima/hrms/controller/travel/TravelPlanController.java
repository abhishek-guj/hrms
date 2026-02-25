package com.roima.hrms.controller.travel;

import com.roima.hrms.dtos.req.TravelEmployeeReqDto;
import com.roima.hrms.dtos.req.TravelPlanRequestDto;
import com.roima.hrms.dtos.res.TravelEmployeeDto;
import com.roima.hrms.dtos.res.TravelExpenseDto;
import com.roima.hrms.dtos.res.TravelPlanDto;
import com.roima.hrms.enums.ApiResponseType;
import com.roima.hrms.response.ApiResponse;
import com.roima.hrms.services.TravelEmployeeService;
import com.roima.hrms.services.TravelExpenseService;
import com.roima.hrms.services.TravelPlanService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/travel-plans")
@Tag(name = "Travel Plan")
public class TravelPlanController {

    private final TravelPlanService travelPlanService;
    private final TravelEmployeeService travelEmployeeService;
    private final TravelExpenseService travelExpenseService;

    public TravelPlanController(TravelPlanService travelPlanService, TravelEmployeeService travelEmployeeService,
            TravelExpenseService travelExpenseService) {
        this.travelPlanService = travelPlanService;
        this.travelEmployeeService = travelEmployeeService;
        this.travelExpenseService = travelExpenseService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllTravelPlans() {
        List<TravelPlanDto> travelPlanDtoList = travelPlanService.getAllTravelPlans();
        ApiResponse<List<TravelPlanDto>> res = ApiResponse.createApiResponse(ApiResponseType.SUCCESS,
                "Fetched all Travel Plans successfully", travelPlanDtoList, null);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createTravelPlan(@RequestBody TravelPlanRequestDto dto) {
        TravelPlanDto tt = travelPlanService.createTravelPlan(dto);
        ApiResponse<TravelPlanDto> res = ApiResponse.createApiResponse(ApiResponseType.SUCCESS,
                "Successfully created Travel Plan.", tt, null);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse> getTravelPlan(@PathVariable Long id) {
        TravelPlanDto tt = travelPlanService.getById(id);
        ApiResponse<TravelPlanDto> res = ApiResponse.createApiResponse(ApiResponseType.SUCCESS,
                "Successfully fetched Travel Plan", tt, null);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PutMapping("{id}")
    public ResponseEntity<ApiResponse> updateTravelPlan(@PathVariable Long id, @RequestBody TravelPlanRequestDto dto) {
        TravelPlanDto travelPlan = travelPlanService.updateTravelPlan(id, dto);
        ApiResponse<TravelPlanDto> res = ApiResponse.createApiResponse(ApiResponseType.SUCCESS,
                "Successfully updated Travel Plan", travelPlan, null);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse> deleteTravelPlan(@PathVariable Long id) {
        travelPlanService.deleteTravelPlan(id);
        ApiResponse<Void> res = ApiResponse.createApiResponse(ApiResponseType.SUCCESS,
                "Successfully deleted Travel Plan", null, null);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    // travel employees

    @GetMapping("{id}/employees")
    public ResponseEntity<ApiResponse> getTravelEmployees(@PathVariable Long id) {
        List<TravelEmployeeDto> travelEmployeeDtoList = travelEmployeeService.getTravelEmployees(id);
        ApiResponse<List<TravelEmployeeDto>> res = ApiResponse.createApiResponse(ApiResponseType.SUCCESS,
                "Successfully fetched Travel Employees", travelEmployeeDtoList, null);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    // @PreAuthorize("hasRole('ROLE_Admin, ROLE_Hr')")
    @PostMapping("{id}/employees")
    public ResponseEntity<ApiResponse> addTravelEmployees(@PathVariable Long id, @RequestBody List<Long> employeeIds) {
        boolean travelEmployees = travelEmployeeService.addTravelEmployees(id, employeeIds);
        ApiResponse<Boolean> res = ApiResponse.createApiResponse(ApiResponseType.SUCCESS,
                "Successfully fetched Travel Employees", travelEmployees, null);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    // @PreAuthorize("hasRole('ROLE_Admin, ROLE_Hr')")
    @DeleteMapping("{id}/employees")
    public ResponseEntity<ApiResponse> deleteTravelEmployees(@PathVariable Long id,
            @RequestBody List<TravelEmployeeReqDto> dto) {
        boolean travelEmployees = travelEmployeeService.deleteTravelEmployees(id, dto);
        ApiResponse<Boolean> res = ApiResponse.createApiResponse(ApiResponseType.SUCCESS,
                "Successfully fetched Travel Employees", travelEmployees, null);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PutMapping("{id}/employees")
    public ResponseEntity<ApiResponse> updateTravelEmployees(@PathVariable Long id,
            @RequestBody List<Long> employeeIds) {
        travelEmployeeService.updateTravelEmployees(id, employeeIds);
        ApiResponse<Boolean> res = ApiResponse.createApiResponse(ApiResponseType.SUCCESS,
                "Successfully added Travel Employees", true, null);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------
    // OPERATIONS ON TRAVEL EXPENSES [WITH REFERNCE TO TRAVELPLAN]
    // -----------------------------------------------------------

    @GetMapping("{travelPlanId}/expenses")
    public ResponseEntity<ApiResponse> getAllTravelExpensesByTravelPlan(@PathVariable Long travelPlanId) {
        List<TravelExpenseDto> travelExpenseDtoList = travelExpenseService
                .getAllTravelExpensesByTravelPlanId(travelPlanId);
        ApiResponse<List<TravelExpenseDto>> res = ApiResponse.createApiResponse(ApiResponseType.SUCCESS,
                "Fetched all Travel Expenses successfully", travelExpenseDtoList, null);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    // -----------------------------------------------------------
    // OPERATIONS ON TRAVEL EXPENSES [WITH REFERNCE TO TRAVELPLAN]
    // -----------------------------------------------------------

}
