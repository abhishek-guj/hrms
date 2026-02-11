package com.roima.hrms.controller;

import com.roima.hrms.dtos.req.LoginReqDto;
import com.roima.hrms.enums.ApiResponseType;
import com.roima.hrms.response.ApiResponse;
import com.roima.hrms.services.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "http://localhost:5173/")
public class AuthController {

    private final AuthService authService;
    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody LoginReqDto dto) {
        try {
            String token = authService.login(dto.email, dto.password);
            log.info("Login");
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.createApiResponse(ApiResponseType.SUCCESS,"logged in...!", token, null));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.createApiResponse(ApiResponseType.ERROR,"Login Failed", ex.getMessage(), null));
        }
    }
}
