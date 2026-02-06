package com.roima.hrms.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "posts", schema = "social")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_post_id", nullable = false)
    private Long id;

    @Column(name = "post_type")
    private Long postType;

    @Column(name = "author_id")
    private Long authorId;

    @Size(max = 255)
    @Nationalized
    @Column(name = "title")
    private String title;

    @Size(max = 255)
    @Nationalized
    @Column(name = "text")
    private String text;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @Column(name = "media_type")
    private Long mediaType;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "deleted_by")
    private Long deletedBy;

    @Size(max = 255)
    @Nationalized
    @Column(name = "delete_reason")
    private String deleteReason;

    @ColumnDefault("1")
    @Column(name = "visible_to_all")
    private Boolean visibleToAll;


}