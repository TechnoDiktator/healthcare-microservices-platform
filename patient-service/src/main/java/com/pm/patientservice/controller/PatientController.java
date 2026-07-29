package com.pm.patientservice.controller;


import com.pm.patientservice.dto.CreatePatientValidationGroup;
import com.pm.patientservice.dto.DoctorResponseDTO;
import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.repository.PatientRepository;
import com.pm.patientservice.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.groups.Default;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
@Tag(name = "Patient" , description = "API for managing Patients")
public class PatientController {

    private final PatientService patientService;
    private final PatientRepository patientRepository;

    public PatientController(PatientService patientService, PatientRepository patientRepository) {
        this.patientService = patientService;
        this.patientRepository = patientRepository;
    }


    @GetMapping
    @Operation(summary = "Get Patients" )
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'DOCTOR')")
    public ResponseEntity<List<PatientResponseDTO>> getPatients() {
        List<PatientResponseDTO> patients = patientService.getPatients();
        return ResponseEntity.ok().body(patients);
    }


    @PostMapping
    @Operation(summary = "Create a new Patient")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'DOCTOR')")
    public ResponseEntity<PatientResponseDTO> createPatient(@Validated({Default.class , CreatePatientValidationGroup.class}) @RequestBody PatientRequestDTO patientRequestDTO) {
        PatientResponseDTO patientResponseDTO = patientService.createPatient(patientRequestDTO);
        return ResponseEntity.ok().body(patientResponseDTO);

    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a new patient")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'DOCTOR')")
    public ResponseEntity<PatientResponseDTO> updatePatient(@PathVariable UUID id , @Validated({Default.class}) @RequestBody PatientRequestDTO patientRequestDTO){
        PatientResponseDTO patientResponseDTO = patientService.updatePatient(id , patientRequestDTO);
        return ResponseEntity.ok().body(patientResponseDTO);

    }


    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete a patient")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'DOCTOR')")
    public ResponseEntity<String> deletePatient(@PathVariable UUID id ){
        patientService.deletePatient(id);
        return ResponseEntity.ok().body("Patient successfully deleted");

    }

    @GetMapping("/{id}/recommended-doctors")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'DOCTOR')")
    public ResponseEntity<List<DoctorResponseDTO>> getRecommendedDoctors(
            @PathVariable UUID id,
            @RequestParam String disease) {

        return ResponseEntity.ok(
                patientService.getRecommendedDoctors(id, disease)
        );
    }
    @GetMapping("/random")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'DOCTOR')")
    public ResponseEntity<PatientResponseDTO> getRandomPatient() {

        return ResponseEntity.ok(
                patientService.getRandomPatient());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get patient by ID")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'DOCTOR')")
    public ResponseEntity<PatientResponseDTO> getPatientById(
            @PathVariable UUID id) {

        return ResponseEntity.ok(
                patientService.getPatientById(id));
    }


}