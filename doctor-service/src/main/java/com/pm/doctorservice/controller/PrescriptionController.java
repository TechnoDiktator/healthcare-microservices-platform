package com.pm.doctorservice.controller;

import com.pm.doctorservice.dto.PrescriptionRequestDTO;
import com.pm.doctorservice.dto.PrescriptionResponseDTO;
import com.pm.doctorservice.security.AuthorizationService;
import com.pm.doctorservice.security.UserContext;
import com.pm.doctorservice.service.PrescriptionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/doctors")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;
    private final AuthorizationService authorizationService;


    public PrescriptionController(
            PrescriptionService prescriptionService,
            AuthorizationService authorizationService) {

        this.prescriptionService = prescriptionService;
        this.authorizationService = authorizationService;
    }

    @PostMapping("/{doctorId}/prescriptions")
    public ResponseEntity<PrescriptionResponseDTO> createPrescription(
            @PathVariable String doctorId,
            @RequestBody @Valid PrescriptionRequestDTO request) {
        UserContext user = getCurrentUser();

        System.out.println(user.getUserId());
        System.out.println(user.getRole());
        System.out.println(user.getEmail());


        authorizationService.authorizeCreatePrescription(
                user,
                doctorId);



        return ResponseEntity.ok(
                prescriptionService.createPrescription(
                        doctorId,
                        request));
    }
    @GetMapping("/prescriptions/{prescriptionId}")
    public ResponseEntity<PrescriptionResponseDTO> getPrescription(
            @PathVariable String prescriptionId) {
        UserContext user = getCurrentUser();

        System.out.println(user.getUserId());
        System.out.println(user.getRole());
        System.out.println(user.getEmail());
        return ResponseEntity.ok(
                prescriptionService.getPrescriptionById(prescriptionId));
    }

    @GetMapping("/{doctorId}/prescriptions")
    public ResponseEntity<List<PrescriptionResponseDTO>> getDoctorPrescriptions(
            @PathVariable UUID doctorId) {
        UserContext user = getCurrentUser();

        System.out.println(user.getUserId());
        System.out.println(user.getRole());
        System.out.println(user.getEmail());
        return ResponseEntity.ok(
                prescriptionService.getPrescriptionsByDoctor(doctorId));
    }
    @GetMapping("/patients/{patientId}/prescriptions")
    public ResponseEntity<List<PrescriptionResponseDTO>> getPatientPrescriptions(
            @PathVariable UUID patientId) {
        UserContext user = getCurrentUser();

        System.out.println(user.getUserId());
        System.out.println(user.getRole());
        System.out.println(user.getEmail());
        return ResponseEntity.ok(
                prescriptionService.getPrescriptionsByPatient(patientId));
    }
    @DeleteMapping("/prescriptions/{prescriptionId}")
    public ResponseEntity<Void> deletePrescription(
            @PathVariable String prescriptionId) {
        UserContext user = getCurrentUser();

        System.out.println(user.getUserId());
        System.out.println(user.getRole());
        System.out.println(user.getEmail());
        prescriptionService.deletePrescription(prescriptionId);

        return ResponseEntity.noContent().build();
    }
    private UserContext getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        return (UserContext) authentication.getPrincipal();
    }

}