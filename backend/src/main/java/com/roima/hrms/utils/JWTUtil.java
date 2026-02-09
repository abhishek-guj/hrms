package com.roima.hrms.utils;


import com.roima.hrms.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.token.Sha512DigestUtils;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

// https://blog.stackademic.com/jwt-authentication-in-spring-boot-a-developers-guide-e79af55dcae8

@Component
public class JWTUtil {

    @Value("${jwt_secret}")
    private String SECRET;


    private Key getSecretKey() {
        return new SecretKeySpec(SECRET.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS256.getJcaName());
    }



    public String generateToken(User user) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole().getRole().name());

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 15))
                .setIssuedAt(new Date())
                .setSubject(user.getEmail())
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractEmail(String jwt) {
        // parsing jwt
        // - build parser
        // - body - subject

        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(jwt)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
