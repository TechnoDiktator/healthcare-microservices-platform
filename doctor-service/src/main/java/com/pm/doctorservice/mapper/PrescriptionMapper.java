package com.pm.doctorservice.mapper;

import com.pm.doctorservice.dto.PrescriptionRequestDTO;
import com.pm.doctorservice.dto.PrescriptionResponseDTO;
import com.pm.doctorservice.model.Prescription;

import java.time.LocalDateTime;
import java.util.UUID;

public class PrescriptionMapper {

    private PrescriptionMapper() {
    }

    public static Prescription toEntity(
            String doctorId,
            PrescriptionRequestDTO dto) {

        Prescription prescription = new Prescription();

        prescription.setPatientId(dto.getPatientId());
        prescription.setDoctorId(doctorId);
        prescription.setDiagnosis(dto.getDiagnosis());
        prescription.setMedicines(dto.getMedicines());
        prescription.setConsultationFee(dto.getConsultationFee());
        prescription.setNotes(dto.getNotes());
        prescription.setPrescribedAt(LocalDateTime.now());

        return prescription;
    }

    public static PrescriptionResponseDTO toDTO(
            Prescription prescription) {

        PrescriptionResponseDTO dto = new PrescriptionResponseDTO();

        dto.setPrescriptionId(prescription.getId());
        dto.setPatientId(prescription.getPatientId());
        dto.setDoctorId(prescription.getDoctorId());
        dto.setDiagnosis(prescription.getDiagnosis());
        dto.setMedicines(prescription.getMedicines());
        dto.setConsultationFee(prescription.getConsultationFee());
        dto.setPrescribedAt(prescription.getPrescribedAt());
        dto.setNotes(prescription.getNotes());
        dto.setBillId(prescription.getBillId());

        return dto;
    }
}