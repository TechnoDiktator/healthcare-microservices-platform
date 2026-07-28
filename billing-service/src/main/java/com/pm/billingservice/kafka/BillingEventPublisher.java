package com.pm.billingservice.kafka;

import billing.events.BillingEvent;
import billing.events.BillingEventType;
import com.pm.billingservice.model.Bill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class BillingEventPublisher {

    private static final Logger log =
            LoggerFactory.getLogger(BillingEventPublisher.class);

    private static final String TOPIC = "billing-events";

    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    public BillingEventPublisher(KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(Bill bill, BillingEventType eventType) {

        BillingEvent event = BillingEvent.newBuilder()
                .setBillId(bill.getId().toString())
                .setPrescriptionId(bill.getPrescriptionId())
                .setPatientId(bill.getPatientId())
                .setDoctorId(bill.getDoctorId())
                .setConsultationFee(bill.getConsultationFee())
                .setMedicineCost(bill.getMedicineCost())
                .setTotalAmount(bill.getTotalAmount())
                .setPaymentStatus(bill.getPaymentStatus().name())
                .setCreatedAt(bill.getCreatedAt().toString())
                .setEventType(eventType)
                .setOccurredAt(System.currentTimeMillis())
                .build();

        kafkaTemplate.send(
                TOPIC,
                bill.getId().toString(),
                event.toByteArray());

        log.info(
                "Published {} event for Bill ID: {}",
                eventType,
                bill.getId());
    }
}