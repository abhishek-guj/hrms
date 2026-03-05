package com.roima.hrms.services;

import org.springframework.stereotype.Service;

import com.roima.hrms.entities.EmployeeProfile;
import com.roima.hrms.entities.Role;
import com.roima.hrms.entities.User;
import com.roima.hrms.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;

    public void updateUserRole(EmployeeProfile employeeProfile, Long roleId) {
        Role role = roleService.getRoleById(roleId);

        User user = employeeProfile.getUser();

        if (user == null) {
            throw new RuntimeException("user not found");
        }

        user.setRole(role);

        userRepository.save(user);
    }

}
