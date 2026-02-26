package com.roima.hrms.repository;

import com.roima.hrms.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    /** All non-deleted posts in reverse chronological order (achievement feed) */
    @Query("SELECT p FROM Post p WHERE p.isDeleted = false ORDER BY p.createdDate DESC, p.id DESC")
    Page<Post> findAllActivePosts(Pageable pageable);

    /** Filter by author */
    @Query("SELECT p FROM Post p WHERE p.isDeleted = false AND p.authorId = :authorId ORDER BY p.createdDate DESC, p.id DESC")
    Page<Post> findByAuthorId(@Param("authorId") Long authorId, Pageable pageable);

    /** Filter by date range */
    @Query("SELECT p FROM Post p WHERE p.isDeleted = false AND p.createdDate BETWEEN :from AND :to ORDER BY p.createdDate DESC, p.id DESC")
    Page<Post> findByDateRange(@Param("from") LocalDate from, @Param("to") LocalDate to, Pageable pageable);

    /** Filter by tag name */
    @Query("SELECT DISTINCT p FROM Post p JOIN p.postTags pt JOIN pt.tag t WHERE p.isDeleted = false AND t.name like %:tagName% ORDER BY p.createdDate DESC, p.id DESC")
    Page<Post> findByTagName(@Param("tagName") String tagName, Pageable pageable);

    /** Filter by author + date range */
    @Query("SELECT p FROM Post p WHERE p.isDeleted = false AND p.authorId = :authorId AND p.createdDate BETWEEN :from AND :to ORDER BY p.createdDate DESC, p.id DESC")
    Page<Post> findByAuthorIdAndDateRange(@Param("authorId") Long authorId,
            @Param("from") LocalDate from,
            @Param("to") LocalDate to,
            Pageable pageable);

    /**
     * Check if a system birthday/anniversary post already exists for today to avoid
     * duplicates
     */
    @Query("SELECT COUNT(p) > 0 FROM Post p WHERE p.isSystemGenerated = true AND p.authorId = :employeeId AND p.createdDate = :today AND p.title LIKE :titleLike")
    boolean existsSystemPostForToday(@Param("employeeId") Long employeeId,
            @Param("today") LocalDate today,
            @Param("titleLike") String titleLike);

    boolean existsByTitleAndIsSystemGeneratedFalse(String string);

    boolean existsByTitleAndIsSystemGeneratedTrue(String string);
}
