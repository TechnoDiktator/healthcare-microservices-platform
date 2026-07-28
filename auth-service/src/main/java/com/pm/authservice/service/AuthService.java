package com.pm.authservice.service;

import com.pm.authservice.dto.InternalUserRequestDTO;
import com.pm.authservice.dto.LoginRequestDTO;
import com.pm.authservice.dto.RegisterRequestDTO;
import com.pm.authservice.exception.EmailAlreadyExistsException;
import com.pm.authservice.exception.InvalidCredentialsException;
import com.pm.authservice.exception.InvalidRoleException;
import com.pm.authservice.model.Role;
import com.pm.authservice.model.User;
import com.pm.authservice.utility.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    public AuthService(UserService userService,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public String authenticate(LoginRequestDTO loginRequestDTO) {

        Optional<User> user = userService.findByEmail(loginRequestDTO.getEmail());

        if (user.isEmpty()) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        boolean matches = passwordEncoder.matches(
                loginRequestDTO.getPassword(),
                user.get().getPassword());

        if (!matches) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        return jwtUtil.generateToken(
                user.get().getEmail(),
                user.get().getRole()
        );
    }

    public boolean validateToken(String token) {
        try {
            jwtUtil.validateToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public User register(RegisterRequestDTO request) {

        if (userService.findByEmail(request.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        if (request.getRole() == Role.ADMIN ||
                request.getRole() == Role.DOCTOR) {

            throw new InvalidRoleException(
                    "This role cannot be registered directly.");
        }

        User user = new User();

        user.setId(UUID.randomUUID());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        return userService.save(user);
    }


    public User createInternalUser(InternalUserRequestDTO request) {

        if (userService.findByEmail(request.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        User user = new User();

        // UUID comes from Doctor/Patient Service
        user.setId(request.getId());

        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        return userService.save(user);
    }


    public Optional<User> getAuthenticatedUser(String token) {

        try {
            jwtUtil.validateToken(token);

            String email = jwtUtil.extractEmail(token);

            return userService.findByEmail(email);

        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public User updateInternalUser(UUID id,
                                   InternalUserRequestDTO request) {

        User user = userService.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        user.setEmail(request.getEmail());

        if (request.getPassword() != null &&
                !request.getPassword().isBlank()) {

            user.setPassword(
                    passwordEncoder.encode(request.getPassword()));
        }

        user.setRole(request.getRole());

        return userService.save(user);
    }
    public void deleteInternalUser(UUID id) {

        User user = userService.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        userService.delete(user);
    }


}