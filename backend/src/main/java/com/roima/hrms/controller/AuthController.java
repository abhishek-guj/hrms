package com.roima.hrms.controller;

import com.roima.hrms.dtos.LoginReqDto;
import com.roima.hrms.response.ApiResponse;
import com.roima.hrms.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("logged in...!", token, null));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse<>("Login Failed", ex.getMessage(), null));
        }
    }
}
