package com.roima.hrms.repository;

import com.roima.hrms.entities.PostType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostTypeRepository extends JpaRepository<PostType, Long> {
}