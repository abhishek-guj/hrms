package com.roima.hrms.dtos.req;

import com.roima.hrms.entities.EmployeeProfile;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobRequestDto {
    @NotBlank(message = "Job title is required")
    private String jobTitle;

    @NotBlank(message = "Job details is required")
    private String jobDetails;


    @NotNull(message = "Exp years is req")
    @Min(value = 0, message = "experience cant be negative")
    private Integer experienceYears;

    @Min(value = 1, message = "vacancy cant be zero")
    @NotNull(message = "No. of vacancy is req")
    private Integer numberOfVaccancy;

    @NotBlank(message = "status is requried")
    private String status; // active or close

    @NotEmpty(message = "atleast one hr required")
    private List<Long> hrIds;

    @NotEmpty(message = "atleast one cv reviewer required")
    private List<Long> cvReviewerIds;

    @NotNull(message = "JD file is required")
    private MultipartFile jobJdFile;
}


