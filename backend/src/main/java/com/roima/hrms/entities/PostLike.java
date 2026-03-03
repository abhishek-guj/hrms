package com.roima.hrms.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

import com.roima.hrms.entities.EmployeeProfile;
import com.roima.hrms.entities.Post;

@Getter
@Setter
@Entity
@Table(name = "post_likes", schema = "social")
public class PostLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_post_like_id", nullable = false)
    private Integer id;

    // optionally keep likeId for backwards compatibility but not used
    @Column(name = "like_id")
    private Long likeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "liked_by_id", nullable = false)
    private EmployeeProfile likedBy;

    @Column(name = "update_on")
    private Instant updateOn;

}