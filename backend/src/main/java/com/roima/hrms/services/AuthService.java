package com.roima.hrms.services;


import com.roima.hrms.entities.User;
import com.roima.hrms.exceptions.EmployeeNotFoundException;
import com.roima.hrms.repository.UserRepository;
import com.roima.hrms.utils.JWTUtil;
import com.roima.hrms.utils.PasswordUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.codec.Utf8;
import org.springframework.stereotype.Service;

import javax.security.auth.login.CredentialException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private JWTUtil jwtUtil;

    public AuthService(JWTUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }


    public String login(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new EmployeeNotFoundException());
        if (user.getPasswordHash().equals(PasswordUtil.hashPassword(password))) {
            return jwtUtil.generateToken(user);
        }

        throw new RuntimeException("Invalid Credentials...!");
    }

}
