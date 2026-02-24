package com.roima.hrms.handlers;

import com.roima.hrms.enums.ApiResponseType;
import com.roima.hrms.exceptions.AuthorizationHeaderMissing;
import com.roima.hrms.exceptions.UnauthorizedException;
import com.roima.hrms.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(AuthorizationHeaderMissing.class)
        ResponseEntity<ApiResponse<Void>> handleAuthorizationHeaderMissing(AuthorizationHeaderMissing ex) {
                ApiResponse<Void> errorResponse = ApiResponse.createApiResponse(
                                ApiResponseType.UNAUTHORIZED,
                                ex.getMessage(),
                                null,
                                null);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }

        @ExceptionHandler(UnauthorizedException.class)
        ResponseEntity<ApiResponse<Void>> handleUnauthorizedException(UnauthorizedException ex) {
                ApiResponse<Void> errorResponse = ApiResponse.createApiResponse(
                                ApiResponseType.UNAUTHORIZED,
                                ex.getMessage(),
                                null,
                                null);
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
        }

        @ExceptionHandler(NoResourceFoundException.class)
        ResponseEntity<ApiResponse<Void>> handleNoResourceFoundException(NoResourceFoundException ex) {
                ApiResponse<Void> errorResponse = ApiResponse.createApiResponse(
                                ApiResponseType.ERROR,
                                ex.getMessage(),
                                null,
                                null);

                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);

        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ApiResponse<Void>> handleValidationExceptions(
                        MethodArgumentNotValidException ex) {
                Map<String, String> errors = new HashMap<>();
                ex.getBindingResult().getFieldErrors()
                                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

                ApiResponse apiError = ApiResponse.createApiResponse(
                                ApiResponseType.ERROR,
                                errors.toString(),
                                null,
                                null);
                return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        @ExceptionHandler(MethodArgumentTypeMismatchException.class)
        public ResponseEntity<ApiResponse<Void>> handleMethodArgumentTypeMismatchException(
                        MethodArgumentTypeMismatchException ex) {
                ApiResponse apiError = ApiResponse.createApiResponse(
                                ApiResponseType.ERROR,
                                "IDENTIFIER not provided OR provided wrong!",
                                null,
                                null);
                return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        @ExceptionHandler(MaxUploadSizeExceededException.class)
        public ResponseEntity<ApiResponse<Void>> handleMaxUploadSizeExceededException(
                        MaxUploadSizeExceededException ex) {
                ApiResponse apiError = ApiResponse.createApiResponse(
                                ApiResponseType.ERROR,
                                "File to large!",
                                null,
                                null);
                return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        @ExceptionHandler(RuntimeException.class)
        public ResponseEntity<ApiResponse<Void>> handleRuntimeException(
                        RuntimeException ex) {
                ApiResponse apiError = ApiResponse.createApiResponse(
                                ApiResponseType.ERROR,
                                ex.getMessage(),
                                null,
                                null);
                return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ApiResponse<Void>> handleBaseException(Exception ex) {

                ApiResponse<Void> errorResponse = ApiResponse.createApiResponse(
                                ApiResponseType.ERROR,
                                // ex.getMessage(),
                                "Internal Server Error",
                                null,
                                null);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
}
