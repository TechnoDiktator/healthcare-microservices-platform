package com.pm.doctorservice.controller;

import com.pm.doctorservice.dto.DoctorRequestDTO;
import com.pm.doctorservice.dto.DoctorResponseDTO;
import com.pm.doctorservice.service.DoctorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.pm.doctorservice.enums.Specialization;

import java.util.List;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public DoctorResponseDTO createDoctor(
            @Valid @RequestBody DoctorRequestDTO requestDTO) {

        return doctorService.createDoctor(requestDTO);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public DoctorResponseDTO getDoctorById(@PathVariable String id) {

        return doctorService.getDoctorById(id);
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<DoctorResponseDTO> getAllDoctors() {

        return doctorService.getAllDoctors();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public DoctorResponseDTO updateDoctor(
            @PathVariable String id,
            @Valid @RequestBody DoctorRequestDTO requestDTO) {

        return doctorService.updateDoctor(id, requestDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteDoctor(@PathVariable String id) {

        doctorService.deleteDoctor(id);

        return ResponseEntity.ok("Doctor deleted successfully.");
    }

    @GetMapping("/specialization/{specialization}")
    @PreAuthorize("isAuthenticated()")
    public List<DoctorResponseDTO> getDoctorsBySpecialization(
            @PathVariable Specialization specialization) {

        return doctorService.getDoctorsBySpecialization(specialization);
    }

    @GetMapping("/recommend")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<DoctorResponseDTO>> recommendDoctors(
            @RequestParam Specialization specialization) {

        return ResponseEntity.ok(
                doctorService.getDoctorsBySpecialization(specialization)
        );
    }


}