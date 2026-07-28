package com.pm.patientservice.kafka;

import com.pm.patientservice.model.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;
import patient.events.PatientEventType;

@Service
public class KafkaProducer {

    private static final Logger log = LoggerFactory.getLogger(KafkaProducer.class);

    private static final String TOPIC = "patient-events";

    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(Patient patient, PatientEventType eventType) {

        PatientEvent event = PatientEvent.newBuilder()
                .setPatientId(patient.getId().toString())
                .setName(patient.getName())
                .setEmail(patient.getEmail())
                .setAddress(patient.getAddress())
                .setDateOfBirth(patient.getDateOfBirth().toString())
                .setRegisteredDate(patient.getRegisteredDate().toString())
                .setEventType(eventType)
                .setOccurredAt(System.currentTimeMillis())
                .build();

        kafkaTemplate.send(
                TOPIC,
                patient.getId().toString(),
                event.toByteArray());

        log.info(
                "Published {} event for Patient ID: {}",
                eventType,
                patient.getId());
    }
}