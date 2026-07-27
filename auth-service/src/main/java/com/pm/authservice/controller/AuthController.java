package com.pm.authservice.controller;

import com.pm.authservice.dto.AuthenticatedUserResponseDTO;
import com.pm.authservice.dto.LoginRequestDTO;
import com.pm.authservice.dto.LoginResponseDTO;
import com.pm.authservice.dto.RegisterRequestDTO;
import com.pm.authservice.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Generate token on user login")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @RequestBody LoginRequestDTO loginRequestDTO){

        Optional<String> tokenOptional  = authService.authenticate(loginRequestDTO);

        if(tokenOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token  = tokenOptional.get();
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
    public ResponseEntity<Void> register( @RequestBody RegisterRequestDTO request) {
        authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }



}
