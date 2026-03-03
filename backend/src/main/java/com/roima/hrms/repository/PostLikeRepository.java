package com.roima.hrms.repository;

import com.roima.hrms.entities.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Integer> {

    /** Check if the user already liked the post */
    boolean existsByPost_IdAndLikedBy_Id(Long postId, Long likedById);

    /** Get the existing like record for unlike operation */
    Optional<PostLike> findByPost_IdAndLikedBy_Id(Long postId, Long likedById);

    /** Count total likes on a post */
    Long countByPost_Id(Long postId);

    /** Recent likers (up to N) – returns likedById list ordered newest first */
    @Query("SELECT pl.likedBy.id FROM PostLike pl WHERE pl.post.id = :postId ORDER BY pl.updateOn DESC")
    List<Long> findRecentLikerIdsByPostId(@Param("postId") Long postId);
}
