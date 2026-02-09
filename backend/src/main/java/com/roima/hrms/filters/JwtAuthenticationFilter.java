package com.roima.hrms.filters;

import com.roima.hrms.exceptions.AuthorizationHeaderMissing;
import com.roima.hrms.repository.UserRepository;
import com.roima.hrms.utils.JWTUtil;
import org.apache.commons.lang3.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(JWTUtil jwtUtil, UserDetailsService userDetailsService, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String requestUri = request.getRequestURI();
        final String jwt;
        final String userEmail;

        if (requestUri.equals("/api/v1/auth/login") || requestUri.equals("/api/v1/auth/signup")) {
            filterChain.doFilter(request, response);
            return;
        }

        if (StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader, "Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        // remove Bearer
        jwt = authHeader.substring(7);


        try {
            userEmail = jwtUtil.extractEmail(jwt);
            com.roima.hrms.entities.User users = userRepository.findByEmail(userEmail).orElseThrow();
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                if (jwtUtil.validateToken(jwt)) {
                    var userDetails = new User(userEmail, "", List.of(new SimpleGrantedAuthority("ROLE_" + users.getRole().getRole().name())));
                    var auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"message\": \"Invalid or expired JWT token\"}");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
