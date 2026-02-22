package com.roima.hrms.repository;

import com.roima.hrms.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    /** All non-deleted comments for a post, oldest first */
    @Query("SELECT c FROM Comment c WHERE c.postId = :postId AND (c.isDeleted = false OR c.isDeleted IS NULL) ORDER BY c.commentedOn ASC")
    List<Comment> findActiveCommentsByPostId(@Param("postId") Long postId);

    /** Count of non-deleted comments for a post */
    @Query("SELECT COUNT(c) FROM Comment c WHERE c.postId = :postId AND (c.isDeleted = false OR c.isDeleted IS NULL)")
    Long countActiveCommentsByPostId(@Param("postId") Long postId);
}
