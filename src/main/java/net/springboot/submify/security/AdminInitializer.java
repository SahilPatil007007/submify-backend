package net.springboot.submify.security;

import jakarta.transaction.Transactional;
import net.springboot.submify.entity.Role;
import net.springboot.submify.entity.Teacher;
import net.springboot.submify.enums.RoleType;
import net.springboot.submify.repository.RoleRepository;
import net.springboot.submify.repository.TeacherRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;

@Component
public class AdminInitializer implements CommandLineRunner {

    private final TeacherRepository teacherRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminInitializer(TeacherRepository teacherRepository,
                            RoleRepository roleRepository,
                            PasswordEncoder passwordEncoder) {
        this.teacherRepository = teacherRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) {
        String adminEmail = "admin@submify.com";

        if (teacherRepository.findByEmail(adminEmail) == null) {
            // Step 1: Create Teacher
            Teacher admin = Teacher.builder()
                    .id(UUID.randomUUID().toString())
                    .name("Admin")
                    .email(adminEmail)
                    .password(passwordEncoder.encode("admin123")) // Default password
                    .build();
            teacherRepository.save(admin);

            // Step 2: Assign Role
            Role adminRole = Role.builder()
                    .roleType(RoleType.ADMIN)
                    .teacher(admin)
                    .build();
            roleRepository.save(adminRole);

            admin.setRoles(Set.of(adminRole));
            teacherRepository.save(admin);
        } else {
            System.out.println("ℹ️ Admin user already exists.");
        }
    }
}