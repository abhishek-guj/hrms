package com.roima.hrms.controller;

import com.roima.hrms.dtos.res.TravelDocumentTypeDto;
import com.roima.hrms.enums.ApiResponseType;
import com.roima.hrms.response.ApiResponse;
import com.roima.hrms.services.FileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/documents")
@CrossOrigin(origins = "*")
@Tag(name = "Documents Controller")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping(value = "expense", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<Object> getExpenseDocument(@RequestParam String id) throws IOException {
        HttpHeaders headers = new HttpHeaders();

        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        Object documentBytes = fileService.getFile("expense", id);

        String fileType = id.substring(id.length() - 3).equals("pdf")
                ? "application/pdf" : "image/" + id.substring(id.length() - 3);

        ResponseEntity<Object>
                responseEntity = ResponseEntity.ok().headers(headers).contentType(
                MediaType.parseMediaType(fileType)).body(documentBytes);
        return responseEntity;

    }

    @GetMapping(value = "job", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<Object> getJobJDFile(@RequestParam String id) throws IOException {
        HttpHeaders headers = new HttpHeaders();

        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        Object documentBytes = fileService.getFile("job", id);

        ResponseEntity<Object>
                responseEntity = ResponseEntity.ok().headers(headers).contentType(
                MediaType.parseMediaType("application/pdf")).body(documentBytes);
        return responseEntity;

    }
}
