package com.roima.hrms.dtos.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class TravelExpenseRequestDto {
    @NotNull(message = "Travelplan id required")
    private Long travelPlanId;

    @NotNull(message = "Expense type id req")
    private Long expenseTypeId;

    @NotBlank(message = "Expense description req")
    private String expenseDescription;

    @NotNull(message = "Expense amt req")
    private BigDecimal expenseAmount;

    @NotBlank(message = "Expense date req")
    private String expenseDate;

    private List<MultipartFile> files;
}
