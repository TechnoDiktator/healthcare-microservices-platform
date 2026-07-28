package com.pm.patientservice.mapper;

import billing.BillResponse;
import billing.PaymentResponse;
import com.pm.patientservice.dto.BillResponseDTO;
import com.pm.patientservice.dto.PaymentResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class BillMapper {

    public BillResponseDTO toDTO(BillResponse response) {

        BillResponseDTO dto = new BillResponseDTO();

        dto.setBillId(response.getBillId());
        dto.setPrescriptionId(response.getPrescriptionId());
        dto.setPatientId(response.getPatientId());
        dto.setDoctorId(response.getDoctorId());

        dto.setConsultationFee(response.getConsultationFee());
        dto.setMedicineCost(response.getMedicineCost());
        dto.setTotalAmount(response.getTotalAmount());

        dto.setPaymentStatus(response.getPaymentStatus());

        return dto;
    }

    public PaymentResponseDTO toDTO(
            PaymentResponse response) {

        PaymentResponseDTO dto =
                new PaymentResponseDTO();

        dto.setBillId(response.getBillId());
        dto.setPaymentStatus(response.getPaymentStatus());

        return dto;
    }
}