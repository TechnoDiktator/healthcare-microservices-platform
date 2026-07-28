package com.pm.doctorservice.security;

import org.springframework.stereotype.Service;
import org.springframework.security.access.AccessDeniedException;
@Service
public class AuthorizationService {

    public void authorizeCreatePrescription(
            UserContext user,
            String doctorId) {

        switch (user.getRole()) {

            case ADMIN:
                return;

            case DOCTOR:

                if (user.getUserId().equals(doctorId))
                    return;

                throw new AccessDeniedException(
                        "Doctors can only create prescriptions for themselves.");

            default:

                throw new AccessDeniedException(
                        "Access denied.");
        }
    }
}
