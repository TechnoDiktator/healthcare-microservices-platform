package com.pm.doctorservice.security;

import com.pm.doctorservice.exception.PrescriptionNotFoundException;
import com.pm.doctorservice.model.Prescription;
import com.pm.doctorservice.repository.PrescriptionRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthorizationService {

    private final PrescriptionRepository prescriptionRepository;

    public AuthorizationService(PrescriptionRepository prescriptionRepository) {
        this.prescriptionRepository = prescriptionRepository;
    }

    public void authorizeCreatePrescription(
            UserContext user,
            String doctorId) {

        switch (user.getRole()) {

            case ADMIN:
                return;

            case DOCTOR:

                if (user.getUserId().equals(doctorId)) {
                    return;
                }

                throw new AccessDeniedException(
                        SecurityMessages.DOCTOR_OWN_PRESCRIPTION);

            default:
                throw new AccessDeniedException(
                        SecurityMessages.ACCESS_DENIED);
        }
    }

    public void authorizeViewPrescription(
            UserContext user,
            String prescriptionId) {

        Prescription prescription = prescriptionRepository.findById(prescriptionId)
                .orElseThrow(() ->
                        new PrescriptionNotFoundException(
                                "Prescription not found."));

        switch (user.getRole()) {

            case ADMIN:
            case STAFF:
            case COMPOUNDER:
                return;

            case DOCTOR:

                if (prescription.getDoctorId().equals(user.getUserId())) {
                    return;
                }

                throw new AccessDeniedException(
                        SecurityMessages.ACCESS_DENIED);

            case PATIENT:

                if (prescription.getPatientId().toString().equals(user.getUserId())) {
                    return;
                }

                throw new AccessDeniedException(
                        SecurityMessages.ACCESS_DENIED);

            default:
                throw new AccessDeniedException(
                        SecurityMessages.ACCESS_DENIED);
        }
    }

    public void authorizeDoctorHistory(
            UserContext user,
            UUID doctorId) {

        switch (user.getRole()) {

            case ADMIN:
            case STAFF:
            case COMPOUNDER:
                return;

            case DOCTOR:

                if (doctorId.toString().equals(user.getUserId())) {
                    return;
                }

                throw new AccessDeniedException(
                        SecurityMessages.DOCTOR_OWN_HISTORY);

            default:
                throw new AccessDeniedException(
                        SecurityMessages.ACCESS_DENIED);
        }
    }

    public void authorizePatientHistory(
            UserContext user,
            UUID patientId) {

        switch (user.getRole()) {

            case ADMIN:
            case STAFF:
            case COMPOUNDER:
            case DOCTOR:
                return;

            case PATIENT:

                if (patientId.toString().equals(user.getUserId())) {
                    return;
                }

                throw new AccessDeniedException(
                        SecurityMessages.PATIENT_OWN_HISTORY);

            default:
                throw new AccessDeniedException(
                        SecurityMessages.ACCESS_DENIED);
        }
    }

    public void authorizeDeletePrescription(
            UserContext user,
            String prescriptionId) {

        Prescription prescription = prescriptionRepository.findById(prescriptionId)
                .orElseThrow(() ->
                        new PrescriptionNotFoundException(
                                "Prescription not found."));

        switch (user.getRole()) {

            case ADMIN:
                return;

            case DOCTOR:

                if (prescription.getDoctorId().equals(user.getUserId())) {
                    return;
                }

                throw new AccessDeniedException(
                        SecurityMessages.DOCTOR_DELETE_OWN);

            default:
                throw new AccessDeniedException(
                        SecurityMessages.ACCESS_DENIED);
        }
    }
}