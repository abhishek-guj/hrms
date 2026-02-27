package com.roima.hrms.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder.Default;

import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "post", schema = "social")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_post_id", nullable = false)
    private Long id;

    @Column(name = "post_type", nullable = false)
    private Long postType;

    @Column(name = "author_id", nullable = false)
    private Long authorId;

    // @Lob
    @Column(name = "title", nullable = false)
    private String title;

    // @Lob
    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate;

    @Column(name = "update_date", nullable = false)
    private LocalDate updateDate;

    @Column(name = "media_type", nullable = false)
    private Long mediaType;

    @Column(name = "is_system_generated")
    private Boolean isSystemGenerated;

    @ColumnDefault("0")
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    /** Nullable – only set when a post is deleted */
    @Column(name = "deleted_by")
    private Long deletedBy;

    /** Nullable – only set when a post is deleted by HR */
    @Column(name = "delete_reason")
    private String deleteReason;

    @ColumnDefault("1")
    @Column(name = "visible_to_all", nullable = false)
    private Boolean visibleToAll;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<PostTag> postTags = new HashSet<>();

    /** If visibleToAll is false, specify departments allowed to view */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "post_departments", schema = "social", joinColumns = @JoinColumn(name = "post_id"), inverseJoinColumns = @JoinColumn(name = "department_id"))
    private Set<Department> visibleDepartments = new HashSet<>();

    /** If visibleToAll is false, specify roles allowed to view */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "post_roles", schema = "social", joinColumns = @JoinColumn(name = "post_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> visibleRoles = new HashSet<>();
}
