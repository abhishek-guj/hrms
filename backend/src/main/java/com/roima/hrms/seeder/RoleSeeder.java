package com.roima.hrms.seeder;


import com.roima.hrms.entities.Role;
import com.roima.hrms.enums.RoleEnum;
import com.roima.hrms.repository.RoleRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class RoleSeeder implements ApplicationListener<ContextRefreshedEvent> {

    private final RoleRepository roleRepository;

    public RoleSeeder(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent){
        this.loadRoles();
    }

    public void loadRoles(){
        RoleEnum[] roleNames = RoleEnum.values();
        Arrays.stream(roleNames).forEach((roleEnum -> {

            boolean roleExists = roleRepository.existsByRole(roleEnum);
            if (!roleExists){
                Role roleCreate = new Role();
                roleCreate.setRole(roleEnum);
                roleRepository.save(roleCreate);
            }
        }));
    }
}
