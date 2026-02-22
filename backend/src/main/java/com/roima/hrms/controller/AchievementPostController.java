package com.roima.hrms.controller;

import com.roima.hrms.dtos.AchievementPostDto;
import com.roima.hrms.dtos.AchievementPostRequestDto;
import com.roima.hrms.dtos.CommentDto;
import com.roima.hrms.dtos.CommentRequestDto;
import com.roima.hrms.enums.ApiResponseType;
import com.roima.hrms.response.ApiResponse;
import com.roima.hrms.services.AchievementPostService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/**
 * REST controller for the Social Achievements & Celebrations feed.
 *
 * Base path: /api/v1/achievements
 */
@RestController
@RequestMapping("/api/v1/achievements")
public class AchievementPostController {

    private final AchievementPostService achievementPostService;

    public AchievementPostController(AchievementPostService achievementPostService) {
        this.achievementPostService = achievementPostService;
    }

    // ─────────────────────────────────────────────
    // POSTS
    // ─────────────────────────────────────────────

    /**
     * GET /api/v1/achievements
     * Paginated achievement feed in reverse chronological order.
     * Supports optional filters: authorId, tag, from, to
     */
    @GetMapping
    public ResponseEntity<ApiResponse<Page<AchievementPostDto>>> getFeed(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Long authorId,
            @RequestParam(required = false) String tag,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {

        Page<AchievementPostDto> result;
        boolean hasFilters = authorId != null || (tag != null && !tag.isBlank()) || from != null || to != null;
        if (hasFilters) {
            result = achievementPostService.getFilteredFeed(authorId, tag, from, to, page, size);
        } else {
            result = achievementPostService.getFeed(page, size);
        }
        return ResponseEntity.ok(ApiResponse.createApiResponse(
                ApiResponseType.SUCCESS, "Achievement feed fetched", result, null));
    }

    /**
     * GET /api/v1/achievements/{postId}
     * Fetch a single post with full like and comment details.
     */
    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponse<AchievementPostDto>> getPost(@PathVariable Long postId) {
        AchievementPostDto dto = achievementPostService.getPostById(postId);
        return ResponseEntity.ok(ApiResponse.createApiResponse(
                ApiResponseType.SUCCESS, "Post fetched", dto, null));
    }

    /**
     * POST /api/v1/achievements
     * Create a new achievement post (any authenticated user).
     */
    @PostMapping
    public ResponseEntity<ApiResponse<AchievementPostDto>> createPost(
            @Valid @RequestBody AchievementPostRequestDto req) {
        AchievementPostDto dto = achievementPostService.createPost(req);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.createApiResponse(ApiResponseType.SUCCESS, "Post created", dto, null));
    }

    /**
     * PUT /api/v1/achievements/{postId}
     * Edit a post (author only).
     */
    @PutMapping("/{postId}")
    public ResponseEntity<ApiResponse<AchievementPostDto>> editPost(
            @PathVariable Long postId,
            @Valid @RequestBody AchievementPostRequestDto req) {
        AchievementPostDto dto = achievementPostService.editPost(postId, req);
        return ResponseEntity.ok(ApiResponse.createApiResponse(
                ApiResponseType.SUCCESS, "Post updated", dto, null));
    }

    /**
     * DELETE /api/v1/achievements/{postId}
     * Delete a post (author or HR/Admin).
     * Optional query param `reason` for HR deletion reason.
     */
    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse<Void>> deletePost(
            @PathVariable Long postId,
            @RequestParam(required = false) String reason) {
        achievementPostService.deletePost(postId, reason);
        return ResponseEntity.ok(ApiResponse.createApiResponse(
                ApiResponseType.SUCCESS, "Post deleted", null, null));
    }

    // ─────────────────────────────────────────────
    // LIKES
    // ─────────────────────────────────────────────

    /**
     * POST /api/v1/achievements/{postId}/like
     * Toggle like/unlike. Returns updated like count.
     */
    @PostMapping("/{postId}/like")
    public ResponseEntity<ApiResponse<Long>> toggleLike(@PathVariable Long postId) {
        Long likeCount = achievementPostService.toggleLike(postId);
        return ResponseEntity.ok(ApiResponse.createApiResponse(
                ApiResponseType.SUCCESS, "Like toggled", likeCount, null));
    }

    // ─────────────────────────────────────────────
    // COMMENTS
    // ─────────────────────────────────────────────

    /**
     * POST /api/v1/achievements/{postId}/comments
     * Add a comment to a post.
     */
    @PostMapping("/{postId}/comments")
    public ResponseEntity<ApiResponse<CommentDto>> addComment(
            @PathVariable Long postId,
            @Valid @RequestBody CommentRequestDto req) {
        CommentDto dto = achievementPostService.addComment(postId, req);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.createApiResponse(ApiResponseType.SUCCESS, "Comment added", dto, null));
    }

    /**
     * PUT /api/v1/achievements/comments/{commentId}
     * Edit a comment (author only).
     */
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse<CommentDto>> editComment(
            @PathVariable Long commentId,
            @Valid @RequestBody CommentRequestDto req) {
        CommentDto dto = achievementPostService.editComment(commentId, req);
        return ResponseEntity.ok(ApiResponse.createApiResponse(
                ApiResponseType.SUCCESS, "Comment updated", dto, null));
    }

    /**
     * DELETE /api/v1/achievements/comments/{commentId}
     * Delete a comment (author or HR/Admin). Optional `reason` param.
     */
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse<Void>> deleteComment(
            @PathVariable Long commentId,
            @RequestParam(required = false) String reason) {
        achievementPostService.deleteComment(commentId, reason);
        return ResponseEntity.ok(ApiResponse.createApiResponse(
                ApiResponseType.SUCCESS, "Comment deleted", null, null));
    }
}
