package com.roima.hrms.dtos.res;


import com.roima.hrms.entities.EmployeeProfile;
import com.roima.hrms.entities.Job;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class JobReferralDto {
    private Long id;
    private Long jobId;
    private String firstName;
    private String lastName;
    private String email;
    private String contactNumber;
    private LocalDateTime referredOn;
    private EmployeeProfileDto referredBy;
    private String cvPath;
    private String note;
    private String status;
    private LocalDateTime statusChangedOn;
}
