package com.roima.hrms.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.time.Instant;

import com.roima.hrms.entities.EmployeeProfile;
import com.roima.hrms.entities.Post;

@Getter
@Setter
@Entity
@Table(name = "comments", schema = "social")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_comment_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id")
    private Comment parentComment;

    @Size(max = 255)
    @Nationalized
    @Column(name = "comment_text")
    private String commentText;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commented_by_id", nullable = false)
    private EmployeeProfile commentedBy;

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