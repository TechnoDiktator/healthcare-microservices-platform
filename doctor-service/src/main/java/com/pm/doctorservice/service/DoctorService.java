package com.pm.doctorservice.service;

import com.pm.doctorservice.dto.DoctorRequestDTO;
import com.pm.doctorservice.dto.DoctorResponseDTO;

import java.util.List;

public interface DoctorService {

    DoctorResponseDTO createDoctor(DoctorRequestDTO requestDTO);

    DoctorResponseDTO getDoctorById(String id);

    List<DoctorResponseDTO> getAllDoctors();

    DoctorResponseDTO updateDoctor(String id, DoctorRequestDTO requestDTO);

    void deleteDoctor(String id);

    List<DoctorResponseDTO> getDoctorsBySpecialization(String specialization);
}