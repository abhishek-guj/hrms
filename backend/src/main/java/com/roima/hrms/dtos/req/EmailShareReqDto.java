package com.roima.hrms.dtos.req;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailShareReqDto {
    @NotBlank(message = "Email Needed")
    @NotEmpty(message = "Email Needed")
    @Email(message = "Invalid Email")
    private String email;
}
