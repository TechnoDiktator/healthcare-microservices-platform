package com.pm.authservice.seeder;

import com.pm.authservice.model.Role;
import com.pm.authservice.model.User;
import com.pm.authservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminSeeder implements CommandLineRunner {

    private static final Logger log =
            LoggerFactory.getLogger(AdminSeeder.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminSeeder(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        if (userRepository.findByEmail("admin@hospital.com").isPresent()) {
            log.info("Admin user already exists. Skipping seeding.");
            return;
        }

        User admin = new User();
        admin.setEmail("admin@hospital.com");
        admin.setFirstName("System");
        admin.setLastName("Administrator");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setRole(Role.ADMIN);

        userRepository.save(admin);

        log.info("Successfully seeded admin user with email: {}", admin.getEmail());
    }
}