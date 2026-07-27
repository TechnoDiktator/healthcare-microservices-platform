package com.pm.authservice.seeder;
import com.pm.authservice.model.Role;
import com.pm.authservice.model.User;
import com.pm.authservice.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminSeeder implements CommandLineRunner {

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
            return;
        }

        User admin = new User();
        admin.setEmail("admin@hospital.com");
        admin.setFirstName("System");
        admin.setLastName("Administrator");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setRole(Role.ADMIN);

        System.out.println("Admin registered"  + admin);
        userRepository.save(admin);
    }
}