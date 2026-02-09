package com.roima.hrms.repository;

import com.roima.hrms.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // just define jpa repos will create action
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);


}