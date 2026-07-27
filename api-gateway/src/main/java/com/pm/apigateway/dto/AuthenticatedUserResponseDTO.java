package com.pm.apigateway.dto;

import java.util.UUID;

public record AuthenticatedUserResponseDTO(
        UUID userId,
        String email,
        String role
) {
}