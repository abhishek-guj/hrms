package com.roima.hrms.seeder;


import com.roima.hrms.entities.EmployeeProfile;
import com.roima.hrms.entities.Role;
import com.roima.hrms.entities.User;
import com.roima.hrms.enums.RoleEnum;
import com.roima.hrms.repository.EmployeeProfileRepository;
import com.roima.hrms.repository.RoleRepository;
import com.roima.hrms.repository.UserRepository;
import com.roima.hrms.utils.PasswordUtil;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class UserSeeder implements ApplicationListener<ContextRefreshedEvent> {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final EmployeeProfileRepository employeeProfileRepository;

    public UserSeeder(UserRepository userRepository, RoleRepository roleRepository, EmployeeProfileRepository employeeProfileRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.employeeProfileRepository = employeeProfileRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.loadUsers();
    }

    public void loadUsers() {


        EmployeeProfile adminProfile;
        EmployeeProfile employeeProfile1;
        EmployeeProfile employeeProfile2;
        EmployeeProfile employeeProfile3;
        EmployeeProfile employeeProfile4;
        EmployeeProfile managerProfile;
        EmployeeProfile managerProfile2;
        EmployeeProfile employeeProfile101;
        EmployeeProfile employeeProfile102;
        EmployeeProfile employeeProfile103;
        EmployeeProfile employeeProfile104;
        EmployeeProfile employeeProfile105;
        if (!employeeProfileRepository.existsByFirstName(("ad"))) {

            adminProfile = EmployeeProfile.builder()
                    .firstName("ad")
                    .lastName("min")
                    .build();
            employeeProfileRepository.save(adminProfile);

            String adminEmail = "abhi@exp.com";
            if (userRepository.existsByEmail(adminEmail)) {
                return;
            }
            Role adminRole = roleRepository.findByRole(RoleEnum.Admin).orElseThrow(() -> new RuntimeException("Role Not Found!"));
            User admin = new User();
            admin.setRole(adminRole);
            admin.setEmail(adminEmail);
            admin.setPasswordHash(PasswordUtil.hashPassword("1234"));
            admin.setEmployeeProfile(adminProfile);
            userRepository.save(admin);
        }
        if (!employeeProfileRepository.existsByFirstName(("emp"))) {

            managerProfile = EmployeeProfile.builder()
                    .firstName("man")
                    .lastName("ager")
                    .manager(employeeProfileRepository.findByFirstName("ad").orElseThrow())
                    .build();
            employeeProfileRepository.save(managerProfile);

            managerProfile2 = EmployeeProfile.builder()
                    .firstName("man2")
                    .lastName("ager2")
                    .manager(employeeProfileRepository.findByFirstName("ad").orElseThrow())
                    .build();
            employeeProfileRepository.save(managerProfile2);

            employeeProfile1 = EmployeeProfile.builder()
                    .firstName("emp")
                    .lastName("loyee")
                    .manager(managerProfile)
                    .build();
            employeeProfileRepository.save(employeeProfile1);

            employeeProfile2 = EmployeeProfile.builder()
                    .firstName("employee")
                    .lastName("number2")
                    .manager(managerProfile)
                    .build();
            employeeProfileRepository.save(employeeProfile2);

            employeeProfile3 = EmployeeProfile.builder()
                    .firstName("employee3")
                    .lastName("number3")
                    .manager(managerProfile)
                    .build();
            employeeProfileRepository.save(employeeProfile3);

            employeeProfile4 = EmployeeProfile.builder()
                    .firstName("employee4")
                    .lastName("number4")
                    .manager(managerProfile2)
                    .build();
            employeeProfileRepository.save(employeeProfile4);

            employeeProfile101 = EmployeeProfile.builder()
                    .firstName("employee05")
                    .lastName("number05")
                    .manager(employeeProfile1)
                    .build();
            employeeProfileRepository.save(employeeProfile101);


            employeeProfile102 = EmployeeProfile.builder()
                    .firstName("employee06")
                    .lastName("number06")
                    .manager(employeeProfile1)
                    .build();
            employeeProfileRepository.save(employeeProfile102);

            employeeProfile103 = EmployeeProfile.builder()
                    .firstName("employee07")
                    .lastName("number07")
                    .manager(employeeProfile2)
                    .build();
            employeeProfileRepository.save(employeeProfile103);


            employeeProfile104 = EmployeeProfile.builder()
                    .firstName("employee08")
                    .lastName("number08")
                    .manager(employeeProfile2)
                    .build();
            employeeProfileRepository.save(employeeProfile104);

            employeeProfile105 = EmployeeProfile.builder()
                    .firstName("employee09")
                    .lastName("number09")
                    .manager(employeeProfile101)
                    .build();
            employeeProfileRepository.save(employeeProfile105);


            // user creation

            String employeeEmail1 = "emp@exp.com";
            if (userRepository.existsByEmail(employeeEmail1)) {
                return;
            }
            Role empRole = roleRepository.findByRole(RoleEnum.Employee).orElseThrow(() -> new RuntimeException("Role Not Found!"));
            User emp1 = new User();
            emp1.setRole(empRole);
            emp1.setEmail(employeeEmail1);
            emp1.setPasswordHash(PasswordUtil.hashPassword("1234"));
            emp1.setEmployeeProfile(employeeProfile1);
            userRepository.save(emp1);

            String employeeEmail2 = "emp2@exp.com";
            if (userRepository.existsByEmail(employeeEmail2)) {
                return;
            }
            User emp2 = new User();
            emp2.setRole(empRole);
            emp2.setEmail(employeeEmail2);
            emp2.setPasswordHash(PasswordUtil.hashPassword("1234"));
            emp2.setEmployeeProfile(employeeProfile2);
            userRepository.save(emp2);

            String employeeEmail3 = "emp3@exp.com";
            if (userRepository.existsByEmail(employeeEmail3)) {
                return;
            }
            User emp3 = new User();
            emp3.setRole(empRole);
            emp3.setEmail(employeeEmail3);
            emp3.setPasswordHash(PasswordUtil.hashPassword("1234"));
            emp3.setEmployeeProfile(employeeProfile3);
            userRepository.save(emp3);


            String employeeEmail4 = "emp4@exp.com";
            if (userRepository.existsByEmail(employeeEmail4)) {
                return;
            }
            User emp4 = new User();
            emp4.setRole(empRole);
            emp4.setEmail(employeeEmail4);
            emp4.setPasswordHash(PasswordUtil.hashPassword("1234"));
            emp4.setEmployeeProfile(employeeProfile4);
            userRepository.save(emp4);


            String managerEmail = "mng@exp.com";
            if(userRepository.existsByEmail(managerEmail)) {
                return;
            }
            Role mngRole = roleRepository.findByRole(RoleEnum.Manager).orElseThrow(() -> new RuntimeException("Role Not Found!"));
            User mng = new User();
            mng.setRole(mngRole);
            mng.setEmail(managerEmail);
            mng.setPasswordHash(PasswordUtil.hashPassword("1234"));
            mng.setEmployeeProfile(managerProfile);
            userRepository.save(mng);
        }
    }
}
