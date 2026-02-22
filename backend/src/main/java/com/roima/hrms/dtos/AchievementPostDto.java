package com.roima.hrms.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AchievementPostDto {
    private Long id;
    private Long authorId;
    private String authorName;
    private String title;
    private String description;
    private List<String> tags;
    private LocalDate createdDate;
    private LocalDate updatedDate;
    private Boolean visibleToAll;
    private Boolean isSystemGenerated;
    private Long likeCount;
    /** Up to 5 most-recent liker names */
    private List<String> recentLikers;
    private Long commentCount;
    private List<CommentDto> comments;
    private Boolean likedByCurrentUser;

    /**
     * List of role names (strings) this post is visible to, if not visible to all.
     */
    private java.util.List<String> visibleRoles;
}
