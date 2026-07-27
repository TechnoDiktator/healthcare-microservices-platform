package com.pm.authservice.dto;

import java.util.UUID;

public record RegisterResponseDTO(
        UUID id,

        String email,
        String role,
        String message



        

) {}