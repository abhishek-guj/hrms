package com.roima.hrms.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

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

    @Lob
    @Column(name = "title", nullable = false)
    private String title;

    @Lob
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

    @ColumnDefault("false")
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    /** Nullable – only set when a post is deleted */
    @Column(name = "deleted_by")
    private Long deletedBy;

    /** Nullable – only set when a post is deleted by HR */
    @Column(name = "delete_reason")
    private String deleteReason;

    @ColumnDefault("true")
    @Column(name = "visible_to_all", nullable = false)
    private Boolean visibleToAll = true;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<PostTag> postTags = new HashSet<>();

    /** If visibleToAll is false, specify who can view the post */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "post_viewers",
        schema = "social",
        joinColumns = @JoinColumn(name = "post_id"),
        inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    private Set<EmployeeProfile> viewers = new HashSet<>();
}
