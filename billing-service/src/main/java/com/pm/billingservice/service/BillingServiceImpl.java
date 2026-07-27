package com.pm.billingservice.service;

import billing.GenerateBillRequest;
import billing.Medicine;
import com.pm.billingservice.model.Bill;
import com.pm.billingservice.model.PaymentStatusEnum;


import com.pm.billingservice.repository.BillRepository;
import com.pm.billingservice.service.BillingService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class BillingServiceImpl implements BillingService {

    private final BillRepository billRepository;

    public BillingServiceImpl(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    @Override
    public Bill generateBill(GenerateBillRequest request) {

        double medicineCost = 0;

        for (Medicine medicine : request.getMedicinesList()) {
            medicineCost +=
                    medicine.getQuantity() *
                            medicine.getUnitPrice();
        }

        double totalAmount =
                medicineCost +
                        request.getConsultationFee();

        Bill bill = new Bill();

        bill.setPrescriptionId(request.getPrescriptionId());
        bill.setPatientId(request.getPatientId());
        bill.setDoctorId(request.getDoctorId());

        bill.setConsultationFee(request.getConsultationFee());
        bill.setMedicineCost(medicineCost);
        bill.setTotalAmount(totalAmount);

        bill.setPaymentStatus(PaymentStatusEnum.PENDING);
        bill.setCreatedAt(LocalDateTime.now());

        return billRepository.save(bill);
    }

    @Override
    public Bill getBill(UUID billId) {

        return billRepository.findById(billId)
                .orElseThrow(() ->
                        new RuntimeException("Bill not found"));
    }

    @Override
    public Bill payBill(UUID billId) {

        Bill bill = billRepository.findById(billId)
                .orElseThrow(() ->
                        new RuntimeException("Bill not found"));

        bill.setPaymentStatus(PaymentStatusEnum.PAID);

        return billRepository.save(bill);
    }
}