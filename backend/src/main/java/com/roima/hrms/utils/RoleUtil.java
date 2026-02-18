package com.roima.hrms.utils;

import com.roima.hrms.entities.EmployeeProfile;
import com.roima.hrms.entities.User;
import com.roima.hrms.exceptions.EmployeeNotFoundException;
import com.roima.hrms.repository.EmployeeProfileRepository;
import com.roima.hrms.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class RoleUtil {

    private final EmployeeProfileRepository employeeProfileRepository;
    private final UserRepository userRepository;

    public RoleUtil(EmployeeProfileRepository employeeProfileRepository, UserRepository userRepository) {
        this.employeeProfileRepository = employeeProfileRepository;
        this.userRepository = userRepository;
    }

    public EmployeeProfile getCurrentEmployee(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        EmployeeProfile employeeProfile = employeeProfileRepository.getEmployeeProfileByUser_Email(auth.getName());
        return employeeProfile;
    }

    public Long getCurrentEmployeeId(){
        EmployeeProfile employeeProfile= getCurrentEmployee();
        return employeeProfile.getId();
    }

    public boolean checkIsManager(Long managerId, Long employeeId){
        EmployeeProfile employee = employeeProfileRepository.findById(employeeId).orElseThrow(EmployeeNotFoundException::new);
        return managerId.equals(employee.getManager().getId());
    }

    public String getRole() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        var a = auth.getName();
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
