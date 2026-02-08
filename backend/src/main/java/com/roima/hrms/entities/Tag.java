package com.roima.hrms.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "tags", schema = "social")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_tag_id", nullable = false)
    private Long id;

    @Size(max = 255)
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "tag")
    private Set<PostTag> postTags = new HashSet<>();
}