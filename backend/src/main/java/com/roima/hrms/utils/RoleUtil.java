package com.roima.hrms.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class RoleUtil {
    public String getRole() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .map(r -> r.startsWith("ROLE_") ? r.substring(5) : r)
                .findFirst()
                .orElse(null);
    }

    public boolean isAdmin() {
        String role = getRole();
        return role.equals("Admin");
    }
    public boolean isHr() {
        String role = getRole();
        return role.equals("Hr");
    }
    public boolean isManager() {
        String role = getRole();
        return role.equals("Manager");
    }
    public boolean isEmployee() {
        String role = getRole();
        return role.equals("Employee");
    }
}
