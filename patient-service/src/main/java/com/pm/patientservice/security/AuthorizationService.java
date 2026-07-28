package com.pm.patientservice.security;



import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import static com.pm.patientservice.enums.Role.ADMIN;
import static com.pm.patientservice.enums.Role.PATIENT;

@Service
public class AuthorizationService {

    public void authorizePatientBillAccess(
            UserContext user,
            String patientId) {

        switch (user.getRole()) {

            case ADMIN:
                return;

            case PATIENT:

                if (user.getUserId().equals(patientId)) {
                    return;
                }

                throw new AccessDeniedException(
                        SecurityMessages.PATIENT_OWN_BILLS);

            default:

                throw new AccessDeniedException(
                        SecurityMessages.ACCESS_DENIED);
        }
    }

    public void authorizePayBill(
            UserContext user,
            String patientId) {

        switch (user.getRole()) {

            case ADMIN:
                return;

            case PATIENT:

                if (user.getUserId().equals(patientId)) {
                    return;
                }

                throw new AccessDeniedException(
                        SecurityMessages.PATIENT_OWN_BILLS);

            default:

                throw new AccessDeniedException(
                        SecurityMessages.ACCESS_DENIED);
        }
    }
}