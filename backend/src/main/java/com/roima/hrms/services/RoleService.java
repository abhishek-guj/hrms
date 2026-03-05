package com.roima.hrms.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.roima.hrms.entities.EmployeeProfile;
import com.roima.hrms.entities.Role;
import com.roima.hrms.entities.User;
import com.roima.hrms.repository.RoleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role getRoleById(Long roleId) {
        return roleRepository.findById(roleId).orElseThrow(() -> new RuntimeException("Role Not Found!"));
    }
}
