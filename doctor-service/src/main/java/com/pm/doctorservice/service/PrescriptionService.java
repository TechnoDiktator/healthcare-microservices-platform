package com.pm.doctorservice.service;

import com.pm.doctorservice.dto.PrescriptionRequestDTO;
import com.pm.doctorservice.dto.PrescriptionResponseDTO;

import java.util.List;
import java.util.UUID;

public interface PrescriptionService {

    PrescriptionResponseDTO createPrescription(
            UUID doctorId,
            PrescriptionRequestDTO request);

    PrescriptionResponseDTO getPrescriptionById(
            String prescriptionId);

    List<PrescriptionResponseDTO> getPrescriptionsByDoctor(
            UUID doctorId);

    List<PrescriptionResponseDTO> getPrescriptionsByPatient(
            UUID patientId);

    void deletePrescription(
            String prescriptionId);
}