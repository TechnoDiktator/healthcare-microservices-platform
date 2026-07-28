package com.pm.doctorservice.authc_service_client;

import com.pm.doctorservice.dto.InternalUserRequestDTO;
import com.pm.doctorservice.dto.InternalUserResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.UUID;

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


    public InternalUserResponseDTO updateInternalUser(
            UUID id,
            InternalUserRequestDTO request) {

        return restClient.put()
                .uri("/internal/users/{id}", id)
                .body(request)
                .retrieve()
                .body(InternalUserResponseDTO.class);
    }

    public void deleteInternalUser(UUID id) {

        restClient.delete()
                .uri("/internal/users/{id}", id)
                .retrieve()
                .toBodilessEntity();
    }



}