package com.pm.billingservice.dto;

import java.util.List;

public class CreateBillRequest {

    private String prescriptionId;

    private String patientId;

    private String doctorId;

    private Double consultationFee;

    private List<MedicineDTO> medicines;

    public String getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public Double getConsultationFee() {
        return consultationFee;
    }

    public void setConsultationFee(Double consultationFee) {
        this.consultationFee = consultationFee;
    }

    public List<MedicineDTO> getMedicines() {
        return medicines;
    }

    public void setMedicines(List<MedicineDTO> medicines) {
        this.medicines = medicines;
    }
}