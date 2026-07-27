package com.pm.billingservice.mapper;

import billing.*;
import billing.BillResponse;
import billing.GenerateBillRequest;
import billing.GenerateBillResponse;
import billing.Medicine;
import billing.PaymentResponse;
import com.pm.billingservice.dto.CreateBillRequest;
import com.pm.billingservice.dto.MedicineDTO;
import com.pm.billingservice.model.Bill;

import java.util.List;

public class BillingMapper {

    private BillingMapper() {
    }

    public static GenerateBillResponse toGenerateBillResponse(Bill bill) {

        return GenerateBillResponse.newBuilder()
                .setBillId(bill.getId().toString())
                .setTotalAmount(bill.getTotalAmount())
                .setPaymentStatus(bill.getPaymentStatus().name())
                .build();
    }

    public static BillResponse toBillResponse(Bill bill) {

        return BillResponse.newBuilder()
                .setBillId(bill.getId().toString())
                .setPrescriptionId(bill.getPrescriptionId())
                .setPatientId(bill.getPatientId())
                .setDoctorId(bill.getDoctorId())
                .setConsultationFee(bill.getConsultationFee())
                .setMedicineCost(bill.getMedicineCost())
                .setTotalAmount(bill.getTotalAmount())
                .setPaymentStatus(bill.getPaymentStatus().name())
                .build();
    }

    public static PaymentResponse toPaymentResponse(Bill bill) {

        return PaymentResponse.newBuilder()
                .setBillId(bill.getId().toString())
                .setPaymentStatus(bill.getPaymentStatus().name())
                .build();
    }

    public static CreateBillRequest toCreateBillRequest(
            GenerateBillRequest request) {

        CreateBillRequest createBillRequest = new CreateBillRequest();

        createBillRequest.setPrescriptionId(request.getPrescriptionId());
        createBillRequest.setPatientId(request.getPatientId());
        createBillRequest.setDoctorId(request.getDoctorId());
        createBillRequest.setConsultationFee(request.getConsultationFee());

        List<MedicineDTO> medicines = request.getMedicinesList()
                .stream()
                .map(BillingMapper::toMedicineDTO)
                .toList();

        createBillRequest.setMedicines(medicines);

        return createBillRequest;
    }

    public static MedicineDTO toMedicineDTO(Medicine medicine) {


        MedicineDTO medicineDTO = new MedicineDTO();

        medicineDTO.setMedicineName(medicine.getMedicineName());
        medicineDTO.setQuantity(medicine.getQuantity());
        medicineDTO.setUnitPrice(medicine.getUnitPrice());

        return medicineDTO;
    }
}