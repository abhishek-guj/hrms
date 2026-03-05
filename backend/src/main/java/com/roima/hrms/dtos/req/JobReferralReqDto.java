package com.roima.hrms.dtos.req;

import com.roima.hrms.entities.EmployeeProfile;
import com.roima.hrms.entities.Job;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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

    @NotBlank(message = "First name required")
    private String firstName;

    @NotBlank(message = "Last name required")
    private String lastName;

    @NotBlank(message = "Email required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Contact number required")
    private String contactNumber;

    @NotNull(message = "Referred by required")
    private Long referredById;

    // validated in service
    private MultipartFile cvFile;

    @NotBlank(message = "Note required")
    private String note;
}
