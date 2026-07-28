package com.pm.doctorservice.authclient;

import com.pm.doctorservice.dto.InternalUserRequestDTO;
import com.pm.doctorservice.dto.InternalUserResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class AuthClient {

    private final RestClient restClient;

    public AuthClient(RestClient.Builder builder,
                      @Value("${auth.service.url}") String authServiceUrl) {

        this.restClient = builder
                .baseUrl(authServiceUrl)
                .build();
    }

    public InternalUserResponseDTO createInternalUser(
            InternalUserRequestDTO request) {

        return restClient.post()
                .uri("/internal/users")
                .body(request)
                .retrieve()
                .body(InternalUserResponseDTO.class);
    }
}