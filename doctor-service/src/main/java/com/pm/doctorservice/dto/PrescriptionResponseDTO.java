package com.pm.doctorservice.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class PrescriptionResponseDTO {

    private String prescriptionId;

    private UUID patientId;

    private String doctorId;

    private String diagnosis;

    private List<String> medicines;

    private Double consultationFee;

    private LocalDateTime prescribedAt;

    private String notes;

    private UUID billId;

    public PrescriptionResponseDTO() {
    }

    public String getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public UUID getPatientId() {
        return patientId;
    }

    public void setPatientId(UUID patientId) {
        this.patientId = patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public List<String> getMedicines() {
        return medicines;
    }

    public void setMedicines(List<String> medicines) {
        this.medicines = medicines;
    }

    public Double getConsultationFee() {
        return consultationFee;
    }

    public void setConsultationFee(Double consultationFee) {
        this.consultationFee = consultationFee;
    }

    public LocalDateTime getPrescribedAt() {
        return prescribedAt;
    }

    public void setPrescribedAt(LocalDateTime prescribedAt) {
        this.prescribedAt = prescribedAt;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public UUID getBillId() {
        return billId;
    }

    public void setBillId(UUID billId) {
        this.billId = billId;
    }
}