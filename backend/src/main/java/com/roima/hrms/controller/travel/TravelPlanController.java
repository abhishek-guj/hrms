package com.roima.hrms.controller.travel;


import com.roima.hrms.dtos.req.TravelPlanRequestDto;
import com.roima.hrms.dtos.res.TravelPlanDto;
import com.roima.hrms.enums.ApiResponseType;
import com.roima.hrms.response.ApiResponse;
import com.roima.hrms.services.TravelPlanService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/travel-plans")
@Tag(name="Travel Plan")
public class TravelPlanController {

    private final TravelPlanService travelPlanService;

    public TravelPlanController(TravelPlanService travelPlanService) {
        this.travelPlanService = travelPlanService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllTravelPlans(){
        List<TravelPlanDto> travelPlanDtoList = travelPlanService.getAllTravelPlans();
        ApiResponse<List<TravelPlanDto>> res = ApiResponse.createApiResponse(ApiResponseType.SUCCESS,"Fetched all Travel Plans successfully", travelPlanDtoList,null);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createTravelPlan(@RequestBody TravelPlanRequestDto dto){
        TravelPlanDto tt = travelPlanService.createTravelPlan(dto);
        ApiResponse<TravelPlanDto> res = ApiResponse.createApiResponse(ApiResponseType.SUCCESS,"Successfully created Travel Plan.", tt,null);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse> getTravelPlan(@PathVariable Long id){
        TravelPlanDto tt = travelPlanService.getById(id);
        ApiResponse<TravelPlanDto> res = ApiResponse.createApiResponse(ApiResponseType.SUCCESS,"Successfully fetched Travel Plan", tt,null);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PutMapping("{id}")
    public ResponseEntity<ApiResponse> updateTravelPlan(@PathVariable Long id, @RequestBody TravelPlanRequestDto dto){
        TravelPlanDto travelPlan = travelPlanService.updateTravelPlan(id, dto);
        ApiResponse<TravelPlanDto> res = ApiResponse.createApiResponse(ApiResponseType.SUCCESS,"Successfully updated Travel Plan", travelPlan,null);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse> deleteTravelPlan(@PathVariable Long id){
        travelPlanService.deleteTravelPlan(id);
        ApiResponse<Void> res = ApiResponse.createApiResponse(ApiResponseType.SUCCESS,"Successfully deleted Travel Plan", null,null);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }
}
