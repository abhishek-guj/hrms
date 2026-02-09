package com.roima.hrms.config;


import com.roima.hrms.exceptions.AuthorizationHeaderMissing;
import com.roima.hrms.exceptions.TravelTypeNotFoundException;
import com.roima.hrms.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthorizationHeaderMissing.class)
    ResponseEntity<ApiResponse<Void>> handleAuthorizationHeaderMissing(AuthorizationHeaderMissing ex) {
        ApiResponse<Void> errorResponse = ApiResponse.createApiResponse(
                ex.getMessage(),
                null,
                null
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    ResponseEntity<ApiResponse<Void>> handleNoResourceFoundException(NoResourceFoundException ex) {
        ApiResponse<Void> errorResponse = ApiResponse.createApiResponse(
                ex.getMessage(),
                null,
                null
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

        ApiResponse apiError = ApiResponse.createApiResponse(
                "Validation error",
                errors,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
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
