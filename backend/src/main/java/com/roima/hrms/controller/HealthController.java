package com.roima.hrms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/v1/health")
@CrossOrigin(origins = "http://localhost:5173/")
public class HealthController {


    @GetMapping
    public ResponseEntity<String> checkHealth(){
        return ResponseEntity.ok("It is up and running....");
    }
}
