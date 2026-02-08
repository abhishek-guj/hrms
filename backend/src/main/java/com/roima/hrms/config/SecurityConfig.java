package com.roima.hrms.config;

import jakarta.servlet.Filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                ; // .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("**").permitAll()
//                        .requestMatchers("/api/v1/auth/**").permitAll()
//                        .requestMatchers("/swagger-ui.html").permitAll()
//                        .requestMatchers("/swagger-ui/*/**").permitAll()
//                        .requestMatchers("/v3/api-docs").permitAll()
//                        .requestMatchers("/v3/api-docs/**").permitAll()
//                        .anyRequest().authenticated());

        return http.build();
    }
}
