package com.roima.hrms.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private String message;
    private T data;
    private Object metadata;

    public static <T> ApiResponse<T> createApiResponse(
           String message,
           T data,
           Object metadata)
    {
        return new ApiResponse<T>(message,data,metadata);
    }
}
