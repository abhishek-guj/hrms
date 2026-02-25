package com.roima.hrms.controller.job;

import com.roima.hrms.dtos.req.*;
import com.roima.hrms.dtos.res.JobDto;
import com.roima.hrms.dtos.res.JobReferralDto;
import com.roima.hrms.enums.ApiResponseType;
import com.roima.hrms.response.ApiResponse;
import com.roima.hrms.services.JobService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/jobs")
@CrossOrigin(origins = "*")
@Tag(name = "Jobs Controller")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    // -----------------------------------------------------------
    // OPERATIONS ON TRAVEL EXPENSES [NO REFERNCE TO TRAVELPLAN]
    // -----------------------------------------------------------
    @GetMapping
    public ResponseEntity<ApiResponse> getAllJobs() {
        List<JobDto> jobDtoList = jobService.getAllJobs();
        ApiResponse<List<JobDto>> res = ApiResponse.createApiResponse(ApiResponseType.SUCCESS,
                "Fetched all Jobs successfully", jobDtoList, null);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> createJob(@ModelAttribute JobRequestDto dto) {
        JobDto jobDto = jobService.createJob(dto);
        ApiResponse<JobDto> res = ApiResponse.createApiResponse(ApiResponseType.SUCCESS, "Successfully created Job.",
                jobDto, null);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    //
    @GetMapping("{jobId}")
    public ResponseEntity<ApiResponse> getJobsById(@PathVariable Long jobId) {
        JobDto jobDto = jobService.getById(jobId);
        ApiResponse<JobDto> res = ApiResponse.createApiResponse(ApiResponseType.SUCCESS,
                "Fetched all Jobs successfully", jobDto, null);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    //
    @PutMapping(value = "{jobId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> updateJob(@PathVariable Long jobId, @ModelAttribute JobRequestDto dto) {
        JobDto job = jobService.updateJob(jobId, dto);
        ApiResponse<JobDto> res = ApiResponse.createApiResponse(ApiResponseType.SUCCESS, "Successfully updated Job",
                job, null);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    //
    @DeleteMapping("{jobId}")
    public ResponseEntity<ApiResponse> deleteJob(@PathVariable Long jobId) {
        jobService.deleteJob(jobId);
        ApiResponse<Void> res = ApiResponse.createApiResponse(ApiResponseType.SUCCESS, "Successfully deleted Job", null,
                null);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    // -----------------------------------------------------------
    // OPERATIONS ON TRAVEL EXPENSES [NO REFERNCE TO TRAVELPLAN]
    // -----------------------------------------------------------

    @PostMapping("{jobId}/share")
    public ResponseEntity<ApiResponse> shareJobById(@PathVariable Long jobId, @RequestBody EmailShareReqDto email) {
        jobService.shareById(jobId, List.of(email));
        ApiResponse<JobDto> res = ApiResponse.createApiResponse(ApiResponseType.SUCCESS, "Shared Job successfully",
                null, null);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PostMapping(value = "{jobId}/refer", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> referJobById(@PathVariable Long jobId,
            @ModelAttribute JobReferralReqDto jobReferralReqDto) {
        boolean createReferral = jobService.referById(jobId, jobReferralReqDto);
        ApiResponse<Boolean> res = ApiResponse.createApiResponse(ApiResponseType.SUCCESS, "Referred successfully",
                createReferral, null);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping("referrals")
    public ResponseEntity<ApiResponse> getAllRefers() {
        List<JobReferralDto> jobDtoList = jobService.getReferrals();
        ApiResponse<List<JobReferralDto>> res = ApiResponse.createApiResponse(ApiResponseType.SUCCESS,
                "Fetched all Referrals successfully", jobDtoList, null);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PutMapping("referrals/{referralId}")
    public ResponseEntity<ApiResponse> updateReferralStatus(@PathVariable Long referralId,
            @RequestBody ReferralStatusDto status) {
        jobService.updateReferralStatus(referralId, status);
        ApiResponse<JobDto> res = ApiResponse.createApiResponse(ApiResponseType.SUCCESS, "Shared Job successfully",
                null, null);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }
}
