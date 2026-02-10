package com.roima.hrms.dtos.req;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpenseTypeRequestDto {
    @NotBlank(message = "Expense type is required!")
    private String name;
}
