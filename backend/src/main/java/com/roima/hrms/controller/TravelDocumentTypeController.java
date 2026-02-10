package com.roima.hrms.controller;


import com.roima.hrms.dtos.req.TravelDocumentTypeRequestDto;
import com.roima.hrms.dtos.res.TravelDocumentTypeDto;
import com.roima.hrms.enums.ApiResponseType;
import com.roima.hrms.response.ApiResponse;
import com.roima.hrms.services.TravelDocumentTypeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/travel-document-types")
@Tags()
@Tag(name="Travel Document Type")
public class TravelDocumentTypeController {

    private final TravelDocumentTypeService travelDocumentTypeService;

    public TravelDocumentTypeController(TravelDocumentTypeService travelDocumentTypeService) {
        this.travelDocumentTypeService = travelDocumentTypeService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllTravelDocumentTypes(){
        List<TravelDocumentTypeDto> travelDocumentTypeDtoList = travelDocumentTypeService.getAllTravelDocumentTypes();
        ApiResponse<List<TravelDocumentTypeDto>> res = ApiResponse.createApiResponse(ApiResponseType.SUCCESS,"Fetched all TravelDocument types successfully", travelDocumentTypeDtoList,null);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createTravelDocumentType(@RequestBody TravelDocumentTypeRequestDto dto){
        TravelDocumentTypeDto tt = travelDocumentTypeService.createTravelDocumentType(dto);
        ApiResponse<TravelDocumentTypeDto> res = ApiResponse.createApiResponse(ApiResponseType.SUCCESS,"Successfully created TravelDocument type.", tt,null);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse> getTravelDocumentType(@PathVariable Long id){
        TravelDocumentTypeDto tt = travelDocumentTypeService.getById(id);
        ApiResponse<TravelDocumentTypeDto> res = ApiResponse.createApiResponse(ApiResponseType.SUCCESS,"Successfully fetched TravelDocument type", tt,null);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PutMapping("{id}")
    public ResponseEntity<ApiResponse> updateTravelDocumentType(@PathVariable Long id, @RequestBody TravelDocumentTypeRequestDto dto){
        TravelDocumentTypeDto travelDocumentType = travelDocumentTypeService.updateTravelDocumentType(id, dto);
        ApiResponse<TravelDocumentTypeDto> res = ApiResponse.createApiResponse(ApiResponseType.SUCCESS,"Successfully updated TravelDocument type", travelDocumentType,null);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse> deleteTravelDocumentType(@PathVariable Long id){
        travelDocumentTypeService.deleteTravelDocumentType(id);
        ApiResponse<Void> res = ApiResponse.createApiResponse(ApiResponseType.SUCCESS,"Successfully deleted TravelDocument type", null,null);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }
}
