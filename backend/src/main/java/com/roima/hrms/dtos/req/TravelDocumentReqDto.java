package com.roima.hrms.dtos.req;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;

import java.util.List;

@Getter
@Setter
public class TravelDocumentReqDto {
    @NotNull(message = "Travel plan is required")
    private Long travelPlanId;

    private Long uploadedForEmployeeId;

    @NotNull(message = "Document type is required")
    private Long documentTypeId;

    // files
    private MultipartFile file;
}
