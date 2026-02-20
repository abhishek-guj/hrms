package com.roima.hrms.dtos.res;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginDto {
    @NotNull
    private String token;
    @NotNull
    private String role;
    @NotNull
    private Long employeeId;
}
