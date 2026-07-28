package com.pm.authservice.controller;

import com.pm.authservice.dto.*;
import com.pm.authservice.model.Role;
import com.pm.authservice.model.User;
import com.pm.authservice.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
public class AuthController {

    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthService authService, PasswordEncoder passwordEncoder) {
        this.authService = authService;
        this.passwordEncoder = passwordEncoder;
    }

    @Operation(summary = "Generate token on user login")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @RequestBody LoginRequestDTO loginRequestDTO){

        String token  = authService.authenticate(loginRequestDTO);

        return ResponseEntity.ok(new LoginResponseDTO(token));

    }



    @Operation(summary = "Validate Token")
    @GetMapping("/validate")
    public ResponseEntity<AuthenticatedUserResponseDTO> validateToken(
            @RequestHeader("Authorization") String authHeader) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return authService
                .getAuthenticatedUser(authHeader.substring(7))
                .map(user -> ResponseEntity.ok(
                        new AuthenticatedUserResponseDTO(
                                user.getId(),
                                user.getEmail(),
                                user.getRole().name()
                        )
                ))
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(
            @RequestBody RegisterRequestDTO request) {

        User user = authService.register(request);

        RegisterResponseDTO response = new RegisterResponseDTO(
                user.getId(),
                user.getEmail(),
                user.getRole().name(),
                "User registered successfully"
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

}
