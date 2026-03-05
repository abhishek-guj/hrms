package com.roima.hrms.dtos.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReferralStatusDto {
    @NotBlank(message = "Referral status required")
    private String status;
}
