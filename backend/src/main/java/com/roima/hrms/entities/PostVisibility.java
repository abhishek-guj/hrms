package com.roima.hrms.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "post_visibilities", schema = "social")
public class PostVisibility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_post_visibility_id", nullable = false)
    private Long id;

    @Column(name = "post_id")
    private Long postId;

    @Column(name = "visible_to")
    private Long visibleTo;


}