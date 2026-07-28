package com.pm.billingservice.service;

import com.pm.billingservice.dto.CreateBillRequest;
import com.pm.billingservice.dto.MedicineDTO;
import com.pm.billingservice.exception.BillNotFoundException;
import com.pm.billingservice.model.Bill;
import com.pm.billingservice.model.PaymentStatusEnum;
import com.pm.billingservice.repository.BillRepository;
import org.springframework.stereotype.Service;
import billing.events.BillingEventType;
import com.pm.billingservice.kafka.BillingEventPublisher;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class BillingServiceImpl implements BillingService {
    private final BillRepository billRepository;
    private final BillingEventPublisher billingEventPublisher;

    public BillingServiceImpl(
            BillRepository billRepository,
            BillingEventPublisher billingEventPublisher) {

        this.billRepository = billRepository;
        this.billingEventPublisher = billingEventPublisher;
    }

    @Override
    public Bill generateBill(CreateBillRequest request) {

        double medicineCost = 0;

        for (MedicineDTO medicine : request.getMedicines()) {
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
        Bill savedBill = billRepository.save(bill);

        billingEventPublisher.sendEvent(
                savedBill,
                BillingEventType.BILL_GENERATED);

        return savedBill;
    }

    @Override
    public Bill getBill(UUID billId) {

        return billRepository.findById(billId)
                .orElseThrow(() ->
                        new BillNotFoundException(
                                "Bill not found with id: " + billId));
    }

    @Override
    public Bill payBill(UUID billId) {

        Bill bill = billRepository.findById(billId)
                .orElseThrow(() ->
                        new BillNotFoundException(
                                "Bill not found with id: " + billId));

        bill.setPaymentStatus(PaymentStatusEnum.PAID);

        bill.setPaymentStatus(PaymentStatusEnum.PAID);

        Bill updatedBill = billRepository.save(bill);

        billingEventPublisher.sendEvent(
                updatedBill,
                BillingEventType.BILL_PAID);

        return updatedBill;
    }
}