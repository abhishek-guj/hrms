package com.roima.hrms.controller;

import com.roima.hrms.dtos.req.EmployeeInterestsReqDto;
import com.roima.hrms.dtos.req.ExpenseTypeRequestDto;
import com.roima.hrms.dtos.res.FullEmployeeProfileDto;
import com.roima.hrms.enums.ApiResponseType;
import com.roima.hrms.response.ApiResponse;
import com.roima.hrms.services.ProfileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profile")
@CrossOrigin(origins = "*")
@Tag(name = "Profile Controller")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getProfile() {
        FullEmployeeProfileDto profile = profileService.getEmployeeProfile();
        ApiResponse<FullEmployeeProfileDto> res = ApiResponse.createApiResponse(ApiResponseType.SUCCESS,
                "Fetched employee profile successfully", profile, null);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PatchMapping("/interests")
    public ResponseEntity<ApiResponse> updateEmployeeProfile(@RequestBody EmployeeInterestsReqDto interests) {
        FullEmployeeProfileDto profile = profileService.updateEmployeeProfile(interests);
        ApiResponse<FullEmployeeProfileDto> res = ApiResponse.createApiResponse(ApiResponseType.SUCCESS,
                "Updated employee profile interests successfully", profile, null);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }
}