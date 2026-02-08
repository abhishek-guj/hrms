package com.roima.hrms.controller;


import com.roima.hrms.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth1/log-in")
public class LoginController {

    @GetMapping
    public ResponseEntity<ApiResponse<String>> Login(){
        ApiResponse<String> response = ApiResponse.createApiResponse(
                "Logged in.......",
                null,
                null);
        return ResponseEntity.ok(response);
    }

}
