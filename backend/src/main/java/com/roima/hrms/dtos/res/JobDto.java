package com.roima.hrms.dtos.res;


import com.roima.hrms.entities.EmployeeProfile;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.Nationalized;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobDto {
    private Long id;
    private String jobTitle;
    private String jobDetails;
    private Integer experienceYears;
    private Integer numberOfVaccancy;
    private EmployeeProfileDto createdBy;
    private LocalDateTime createdOn;
    private EmployeeProfileDto updatedBy;
    private LocalDateTime updatedOn;
    private String status; // active or close
    private LocalDateTime statusChangedOn;
    private JobJdFileDto jobJdFile;
}


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
class JobJdFileDto{
    private Long id;
    private String filePath;
    private LocalDateTime uploadedOn;
    private EmployeeProfileDto uploadedBy;
}
