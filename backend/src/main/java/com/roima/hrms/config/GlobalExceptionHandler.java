package com.roima.hrms.config;


import com.roima.hrms.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(NoResourceFoundException.class)
    ResponseEntity<ApiResponse<Void>> handleNoResourceFoundException(NoResourceFoundException ex) {
        ApiResponse<Void> errorResponse = ApiResponse.createApiResponse(
                ex.getMessage(),
                null,
                null
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);

    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleBaseException(Exception ex) {
        ApiResponse<Void> errorResponse = ApiResponse.createApiResponse(
                "An Error Occured", null,
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
