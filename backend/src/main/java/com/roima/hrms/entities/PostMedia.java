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
@Table(name = "post_medias", schema = "social")
public class PostMedia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_post_media_id", nullable = false)
    private Long id;

    @Column(name = "post_id")
    private Long postId;

    @Size(max = 255)
    @Nationalized
    @Column(name = "file_type")
    private String fileType;

    @Size(max = 255)
    @Nationalized
    @Column(name = "file_path")
    private String filePath;

    @Size(max = 255)
    @Nationalized
    @Column(name = "file_name")
    private String fileName;

    @Column(name = "update_date")
    private Instant updateDate;


}