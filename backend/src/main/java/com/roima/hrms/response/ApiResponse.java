package com.roima.hrms.response;

import com.roima.hrms.enums.ApiResponseType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private ApiResponseType status;
    private String message;
    private T data;
    private Object metadata;
    private Instant timestamp;

    public static <T> ApiResponse<T> createApiResponse(
            ApiResponseType status,
            String message,
            T data,
            Object metadata) {
        return new ApiResponse<T>(status, message, data, metadata, Instant.now());
    }
}
