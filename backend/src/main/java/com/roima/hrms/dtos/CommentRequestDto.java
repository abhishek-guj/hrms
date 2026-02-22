package com.roima.hrms.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {

    @NotBlank(message = "Comment text is required")
    private String commentText;

    /** Optional – set to reply to a parent comment */
    private Long parentCommentId;
}
