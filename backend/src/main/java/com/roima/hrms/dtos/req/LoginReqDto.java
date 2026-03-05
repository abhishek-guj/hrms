package com.roima.hrms.dtos.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public class LoginReqDto {
    @NotBlank(message = "email required")
    public String email;

    @NotBlank(message = "password required")
    public String password;
}
