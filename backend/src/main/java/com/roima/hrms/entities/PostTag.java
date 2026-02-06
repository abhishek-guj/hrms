package com.roima.hrms.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "post_tags", schema = "social")
public class PostTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_tag_id", nullable = false)
    private Long id;

    @Column(name = "post_id")
    private Long postId;

    @Column(name = "tag_id")
    private Long tagId;


}