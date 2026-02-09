package com.roima.hrms.controller;


import com.roima.hrms.dtos.req.TravelTypeRequestDto;
import com.roima.hrms.dtos.res.TravelTypeDto;
import com.roima.hrms.entities.TravelType;
import com.roima.hrms.response.ApiResponse;
import com.roima.hrms.services.TravelTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/travel-types")
public class TravelTypeController {

    private final TravelTypeService travelTypeService;

    public TravelTypeController(TravelTypeService travelTypeService) {
        this.travelTypeService = travelTypeService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllTraveltypes(){
        List<TravelTypeDto> travelTypeDtoList = travelTypeService.getAllTravelTypes();
        ApiResponse<List<TravelTypeDto>> res = ApiResponse.createApiResponse("Fetched all travel types successfully", travelTypeDtoList,null);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createTraveltype(@RequestBody TravelTypeRequestDto dto){
        TravelTypeDto tt = travelTypeService.createTravelType(dto);
        ApiResponse<TravelTypeDto> res = ApiResponse.createApiResponse("Successfully created Travel type.", tt,null);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse> getTravelType(@PathVariable Long id){
        TravelTypeDto tt = travelTypeService.getById(id);
        ApiResponse<TravelTypeDto> res = ApiResponse.createApiResponse("Successfully fetched travel type", tt,null);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PutMapping("{id}")
    public ResponseEntity<ApiResponse> updateTravelType(@PathVariable Long id, @RequestBody TravelTypeRequestDto dto){
        TravelTypeDto travelType = travelTypeService.updateTravelType(id, dto);
        ApiResponse<TravelTypeDto> res = ApiResponse.createApiResponse("Successfully updated travel type", travelType,null);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse> deleteTravelType(@PathVariable Long id){
        travelTypeService.deleteTravelType(id);
        ApiResponse<Void> res = ApiResponse.createApiResponse("Successfully deleted travel type", null,null);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }
}
