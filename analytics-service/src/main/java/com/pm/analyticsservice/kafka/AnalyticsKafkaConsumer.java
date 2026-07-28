package com.pm.analyticsservice.kafka;

import billing.events.BillingEvent;
import com.google.protobuf.InvalidProtocolBufferException;
import doctor.events.DoctorEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;
import prescription.events.PrescriptionEvent;

@Service
public class AnalyticsKafkaConsumer {

    private static final Logger log =
            LoggerFactory.getLogger(AnalyticsKafkaConsumer.class);

    @KafkaListener(
            topics = "patient-events",
            groupId = "analytics-service")
    public void consumePatientEvent(byte[] payload) {

        try {

            PatientEvent event =
                    PatientEvent.parseFrom(payload);

            log.info("Received Patient Event: {}", event);

            // analyticsService.handlePatientEvent(event);

        } catch (InvalidProtocolBufferException e) {

            log.error("Failed to deserialize PatientEvent", e);
        }
    }

    @KafkaListener(
            topics = "doctor-events",
            groupId = "analytics-service")
    public void consumeDoctorEvent(byte[] payload) {

        try {

            DoctorEvent event =
                    DoctorEvent.parseFrom(payload);

            log.info("Received Doctor Event: {}", event);

            // analyticsService.handleDoctorEvent(event);

        } catch (InvalidProtocolBufferException e) {

            log.error("Failed to deserialize DoctorEvent", e);
        }
    }

    @KafkaListener(
            topics = "billing-events",
            groupId = "analytics-service")
    public void consumeBillingEvent(byte[] payload) {

        try {

            BillingEvent event =
                    BillingEvent.parseFrom(payload);

            log.info("Received Billing Event: {}", event);

            // analyticsService.handleBillingEvent(event);

        } catch (InvalidProtocolBufferException e) {

            log.error("Failed to deserialize BillingEvent", e);
        }
    }

    @KafkaListener(
            topics = "prescription-events",
            groupId = "analytics-service")
    public void consumePrescriptionEvent(byte[] payload) {

        try {

            PrescriptionEvent event =
                    PrescriptionEvent.parseFrom(payload);

            log.info("Received Prescription Event: {}", event);

            // analyticsService.handlePrescriptionEvent(event);

        } catch (InvalidProtocolBufferException e) {

            log.error("Failed to deserialize PrescriptionEvent", e);
        }
    }
}