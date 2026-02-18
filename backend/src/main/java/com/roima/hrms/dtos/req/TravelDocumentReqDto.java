package com.roima.hrms.dtos.req;


import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class TravelDocumentReqDto {
    private Long travelPlanId;
    private Long uploadedByEmployeeId;
    private Long uploadedForEmployeeId;
    private Long documentTypeId;

    // hr or employee
    private String ownertype;

    // files
    private MultipartFile file;
}
