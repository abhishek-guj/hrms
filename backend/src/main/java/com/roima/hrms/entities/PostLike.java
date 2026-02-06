package com.roima.hrms.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "post_likes", schema = "social")
public class PostLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_post_like_id", nullable = false)
    private Integer id;

    @Column(name = "like_id")
    private Long likeId;

    @Column(name = "post_id")
    private Long postId;

    @Column(name = "liked_by_id")
    private Long likedById;

    @Column(name = "update_on")
    private Instant updateOn;


}