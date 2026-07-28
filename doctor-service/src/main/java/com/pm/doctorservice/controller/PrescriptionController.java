package com.pm.doctorservice.controller;

import com.pm.doctorservice.dto.PrescriptionRequestDTO;
import com.pm.doctorservice.dto.PrescriptionResponseDTO;
import com.pm.doctorservice.service.PrescriptionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/doctors")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    public PrescriptionController(
            PrescriptionService prescriptionService) {

        this.prescriptionService = prescriptionService;
    }

    @PostMapping("/{doctorId}/prescriptions")
    public ResponseEntity<PrescriptionResponseDTO> createPrescription(
            @PathVariable String doctorId,
            @RequestBody @Valid PrescriptionRequestDTO request) {

        return ResponseEntity.ok(
                prescriptionService.createPrescription(
                        doctorId,
                        request));
    }
}