package com.roima.hrms.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "comments", schema = "social")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_comment_id", nullable = false)
    private Long id;

    @Column(name = "post_id")
    private Long postId;

    @Column(name = "parent_comment_id")
    private Long parentCommentId;

    @Size(max = 255)
    @Nationalized
    @Column(name = "comment_text")
    private String commentText;

    @Column(name = "commented_by_id")
    private Long commentedById;

    @Column(name = "commented_on")
    private Instant commentedOn;

    @Column(name = "updated_on")
    private Instant updatedOn;

    @Column(name = "deleted_by")
    private Long deletedBy;

    @Column(name = "deleted_on")
    private Instant deletedOn;

    @Column(name = "is_deleted")
    private Boolean isDeleted;


}