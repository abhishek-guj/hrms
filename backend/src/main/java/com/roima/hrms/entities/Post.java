package com.roima.hrms.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "posts", schema = "social")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_post_id", nullable = false)
    private Long id;

    @Column(name = "post_type", nullable = false)
    private Long postType;

    @Column(name = "author_id", nullable = false)
    private Long authorId;

    @Size(max = 255)
    @Column(name = "title", nullable = false)
    private String title;

    @Size(max = 255)
    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate;

    @Column(name = "update_date", nullable = false)
    private LocalDate updateDate;

    @Column(name = "media_type", nullable = false)
    private Long mediaType;

    @Column(name = "is_system_generated", nullable = false)
    private Boolean isSystemGenerated;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @Column(name = "deleted_by", nullable = false)
    private Long deletedBy;

    @Size(max = 255)
    @Column(name = "delete_reason", nullable = false)
    private String deleteReason;

    @ColumnDefault("1")
    @Column(name = "visible_to_all", nullable = false)
    private Boolean visibleToAll;

    @OneToMany(
            mappedBy = "post",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<PostTag> postTags = new HashSet<>();
}