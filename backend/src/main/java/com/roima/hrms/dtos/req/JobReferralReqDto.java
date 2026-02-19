package com.roima.hrms.dtos.req;


import com.roima.hrms.entities.EmployeeProfile;
import com.roima.hrms.entities.Job;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobReferralReqDto {
    private Long jobId;
    private String firstName;
    private String lastName;
    private String email;
    private String contactNumber;
    private Long referredById;
    private MultipartFile cvFile;
    private String note;
}
