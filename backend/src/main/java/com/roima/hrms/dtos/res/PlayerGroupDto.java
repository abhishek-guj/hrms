package com.roima.hrms.dtos.res;

import com.roima.hrms.entities.EmployeeProfile;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlayerGroupDto {
    private Long id;
    private List<EmployeeProfileDto> players;
}
