package com.roima.hrms.controller.job;


import com.roima.hrms.dtos.res.JobDto;
import com.roima.hrms.dtos.req.JobRequestDto;
import com.roima.hrms.enums.ApiResponseType;
import com.roima.hrms.response.ApiResponse;
import com.roima.hrms.services.JobService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
        ApiResponse<List<JobDto>> res = ApiResponse.createApiResponse(ApiResponseType.SUCCESS, "Fetched all Jobs successfully", jobDtoList, null);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }


    //    @PostMapping
//    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<ApiResponse> createJob(@ModelAttribute JobRequestDto dto) {
//        JobDto tt = jobService.createJob(dto);
//        ApiResponse<JobDto> res = ApiResponse.createApiResponse(ApiResponseType.SUCCESS, "Successfully created Job.", tt, null);
//        return ResponseEntity.status(HttpStatus.OK).body(res);
//    }
//
    @GetMapping("{jobId}")
    public ResponseEntity<ApiResponse> getJobsById(@PathVariable Long jobId) {
        JobDto jobDto = jobService.getById(jobId);
        ApiResponse<JobDto> res = ApiResponse.createApiResponse(ApiResponseType.SUCCESS, "Fetched all Jobs successfully", jobDto, null);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }
//
//    @PutMapping("{jobId}")
//    public ResponseEntity<ApiResponse> updateJob(@PathVariable Long id, @RequestBody JobRequestDto dto) {
//        JobDto job = jobService.updateJob(id, dto);
//        ApiResponse<JobDto> res = ApiResponse.createApiResponse(ApiResponseType.SUCCESS, "Successfully updated Job", job, null);
//        return ResponseEntity.status(HttpStatus.OK).body(res);
//    }
//
    @DeleteMapping("{jobId}")
    public ResponseEntity<ApiResponse> deleteJob(@PathVariable Long jobId) {
        jobService.deleteJob(jobId);
        ApiResponse<Void> res = ApiResponse.createApiResponse(ApiResponseType.SUCCESS, "Successfully deleted Job", null, null);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }


    // -----------------------------------------------------------
    // OPERATIONS ON TRAVEL EXPENSES [NO REFERNCE TO TRAVELPLAN]
    // -----------------------------------------------------------


}
