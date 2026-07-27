package com.pm.doctorservice.controller;

import com.pm.doctorservice.dto.DoctorRequestDTO;
import com.pm.doctorservice.dto.DoctorResponseDTO;
import com.pm.doctorservice.service.DoctorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DoctorResponseDTO createDoctor(
            @Valid @RequestBody DoctorRequestDTO requestDTO) {

        return doctorService.createDoctor(requestDTO);
    }

    @GetMapping("/{id}")
    public DoctorResponseDTO getDoctorById(@PathVariable String id) {

        return doctorService.getDoctorById(id);
    }

    @GetMapping
    public List<DoctorResponseDTO> getAllDoctors() {

        return doctorService.getAllDoctors();
    }

    @PutMapping("/{id}")
    public DoctorResponseDTO updateDoctor(
            @PathVariable String id,
            @Valid @RequestBody DoctorRequestDTO requestDTO) {

        return doctorService.updateDoctor(id, requestDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDoctor(@PathVariable String id) {

        doctorService.deleteDoctor(id);
    }

    @GetMapping("/specialization/{specialization}")
    public List<DoctorResponseDTO> getDoctorsBySpecialization(
            @PathVariable String specialization) {

        return doctorService.getDoctorsBySpecialization(specialization);
    }
}