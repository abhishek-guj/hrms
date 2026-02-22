package com.roima.hrms.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AchievementPostRequestDto {

    @NotBlank(message = "Title is required")
    @Size(max = 255)
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    /** List of tag names (will be created if not existing) */
    private List<String> tags;

    /** Defaults to true (visible to all employees) */
    private Boolean visibleToAll = true;

    /** If visibleToAll is false, specify department IDs allowed to view */
    private List<Long> departmentIds;

    /** If visibleToAll is false, specify role IDs allowed to view */
    private List<Long> roleIds;
}
