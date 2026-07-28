package com.pm.doctorservice.kafka;

import doctor.events.DoctorEvent;
import doctor.events.DoctorEventType;
import com.pm.doctorservice.model.Doctor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class DoctorEventPublisher {

    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    public DoctorEventPublisher(KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(
            Doctor doctor,
            DoctorEventType eventType) {

        DoctorEvent event = DoctorEvent.newBuilder()
                .setDoctorId(doctor.getId())
                .setFirstName(doctor.getFirstName())
                .setLastName(doctor.getLastName())
                .setEmail(doctor.getEmail())
                .setSpecialization(
                        doctor.getSpecialization().name())
                .setPhoneNumber(doctor.getPhoneNumber())
                .setQualification(doctor.getQualification())
                .setExperience(doctor.getExperience())
                .setAvailable(doctor.getAvailable())
                .setEventType(eventType)
                .setOccurredAt(System.currentTimeMillis())
                .build();

        kafkaTemplate.send(
                "doctor-events",
                doctor.getId(),
                event.toByteArray());
    }
}