package com.roima.hrms.repository;

import com.roima.hrms.entities.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Integer> {

    /** Check if the user already liked the post */
    boolean existsByPostIdAndLikedById(Long postId, Long likedById);

    /** Get the existing like record for unlike operation */
    Optional<PostLike> findByPostIdAndLikedById(Long postId, Long likedById);

    /** Count total likes on a post */
    Long countByPostId(Long postId);

    /** Recent likers (up to N) – returns likedById list ordered newest first */
    @Query("SELECT pl.likedById FROM PostLike pl WHERE pl.postId = :postId ORDER BY pl.updateOn DESC")
    List<Long> findRecentLikerIdsByPostId(@Param("postId") Long postId);
}
