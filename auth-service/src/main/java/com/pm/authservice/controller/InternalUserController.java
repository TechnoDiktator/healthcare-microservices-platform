package com.pm.authservice.controller;

import com.pm.authservice.dto.InternalUserRequestDTO;
import com.pm.authservice.dto.InternalUserResponseDTO;
import com.pm.authservice.model.User;
import com.pm.authservice.service.AuthService;
import com.pm.authservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/internal")
public class InternalUserController {

    private final AuthService authService;

    public InternalUserController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/users")
    public ResponseEntity<InternalUserResponseDTO> createUser(
            @RequestBody @Valid InternalUserRequestDTO request) {

        User user = authService.createInternalUser(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(toResponse(user));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<InternalUserResponseDTO> updateUser(
            @PathVariable UUID id,
            @RequestBody @Valid InternalUserRequestDTO request) {

        User user = authService.updateInternalUser(id, request);

        return ResponseEntity.ok(toResponse(user));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {

        authService.deleteInternalUser(id);

        return ResponseEntity.noContent().build();
    }

    private InternalUserResponseDTO toResponse(User user) {

        return new InternalUserResponseDTO(
                user.getId(),
                user.getEmail(),
                user.getRole().name()
        );
    }
}