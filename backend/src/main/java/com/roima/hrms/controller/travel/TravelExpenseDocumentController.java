package com.roima.hrms.controller.travel;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roima.hrms.dtos.req.TravelExpenseRequestDto;
import com.roima.hrms.enums.ApiResponseType;
import com.roima.hrms.response.ApiResponse;
import com.roima.hrms.services.FileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/travel-expense/documents")
@CrossOrigin(origins = "*")
@Tag(name = "Travel Expense")
public class TravelExpenseDocumentController {

    private final FileService fileService;

    public TravelExpenseDocumentController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping(value = "{expenseId}/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> expenseUpload(@PathVariable Long expenseId,@ModelAttribute TravelExpenseRequestDto dto) throws IOException {
        ApiResponse<String> res = ApiResponse.createApiResponse(ApiResponseType.SUCCESS, "Successfully created Travel Plan.", null
                , null);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }
}
