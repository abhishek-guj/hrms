package com.roima.hrms.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private Long id;
    private Long postId;
    private Long parentCommentId;
    private String commentText;
    private Long commentedById;
    private String commenterName;   // firstName + lastName of commenter
    private Instant commentedOn;
    private Instant updatedOn;
    private Boolean isDeleted;
}
