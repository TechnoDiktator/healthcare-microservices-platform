package com.pm.analyticsservice.kafka;

import billing.events.BillingEvent;
import com.google.protobuf.InvalidProtocolBufferException;
import com.pm.analyticsservice.service.AnalyticsService;
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

    private final AnalyticsService analyticsService;

    public AnalyticsKafkaConsumer(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @KafkaListener(
            topics = "patient-events")
    public void consumePatientEvent(byte[] payload) {

        try {

            PatientEvent event = PatientEvent.parseFrom(payload);

            log.info("Received Patient Event: {}", event);

            analyticsService.handlePatientEvent(event);

        } catch (InvalidProtocolBufferException e) {

            log.error("Failed to deserialize PatientEvent", e);
        } catch (Exception e) {

            log.error("Failed to process PatientEvent", e);
        }
    }

    @KafkaListener(
            topics = "doctor-events")
    public void consumeDoctorEvent(byte[] payload) {

        try {

            DoctorEvent event = DoctorEvent.parseFrom(payload);

            log.info("Received Doctor Event: {}", event);

            analyticsService.handleDoctorEvent(event);

        } catch (InvalidProtocolBufferException e) {

            log.error("Failed to deserialize DoctorEvent", e);
        } catch (Exception e) {

            log.error("Failed to process DoctorEvent", e);
        }
    }

    @KafkaListener(
            topics = "billing-events")
    public void consumeBillingEvent(byte[] payload) {

        try {

            BillingEvent event = BillingEvent.parseFrom(payload);

            log.info("Received Billing Event: {}", event);

            analyticsService.handleBillingEvent(event);

        } catch (InvalidProtocolBufferException e) {

            log.error("Failed to deserialize BillingEvent", e);
        } catch (Exception e) {

            log.error("Failed to process BillingEvent", e);
        }
    }

    @KafkaListener(
            topics = "prescription-events")
    public void consumePrescriptionEvent(byte[] payload) {

        try {

            PrescriptionEvent event = PrescriptionEvent.parseFrom(payload);

            log.info("Received Prescription Event: {}", event);

            analyticsService.handlePrescriptionEvent(event);

        } catch (InvalidProtocolBufferException e) {

            log.error("Failed to deserialize PrescriptionEvent", e);
        } catch (Exception e) {

            log.error("Failed to process PrescriptionEvent", e);
        }
    }
}