package com.roima.hrms.seeder;


import com.roima.hrms.entities.Role;
import com.roima.hrms.entities.User;
import com.roima.hrms.enums.RoleEnum;
import com.roima.hrms.repository.RoleRepository;
import com.roima.hrms.repository.UserRepository;
import com.roima.hrms.utils.PasswordUtil;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class UserSeeder implements ApplicationListener<ContextRefreshedEvent> {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserSeeder(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.loadUsers();
    }

    public void loadUsers() {

        String adminEmail = "abhi@exp.com";
        if (userRepository.existsByEmail(adminEmail)) {
            return;
        }
        Role adminRole = roleRepository.findByRole(RoleEnum.Admin).orElseThrow(() -> new RuntimeException("Role Not Found!"));
        User admin = new User();
        admin.setRole(adminRole);
        admin.setEmail(adminEmail);
        admin.setPasswordHash(PasswordUtil.hashPassword("1234"));
        userRepository.save(admin);

        String employeeEmail = "emp@exp.com";
        if (userRepository.existsByEmail(employeeEmail)) {
            return;
        }
        Role empRole = roleRepository.findByRole(RoleEnum.Employee).orElseThrow(() -> new RuntimeException("Role Not Found!"));
        User emp = new User();
        emp.setRole(empRole);
        emp.setEmail(employeeEmail);
        emp.setPasswordHash(PasswordUtil.hashPassword("1234"));
        userRepository.save(emp);
    }
}
