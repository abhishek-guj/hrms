package com.roima.hrms.dtos.req;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
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
    private Long travelPlanId;
    private Long submittedById;
    private Long expenseTypeId;

    private Boolean submitStatus;
    private String expenseUploadDate;
    private BigDecimal expenseAmount;
    private String expenseDate;
    private String status;
    private String remark;

    private List<MultipartFile> files;
}
