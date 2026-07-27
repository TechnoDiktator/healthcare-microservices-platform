package com.pm.authservice.dto;



import java.util.List;
import java.util.UUID;

public record AuthenticatedUserResponseDTO(
        UUID userId,
        String email,
        String role
) {
}