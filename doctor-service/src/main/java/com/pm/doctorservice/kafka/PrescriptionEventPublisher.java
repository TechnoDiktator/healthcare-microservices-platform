package com.pm.doctorservice.kafka;

import com.pm.doctorservice.model.Prescription;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import prescription.events.PrescriptionEvent;
import prescription.events.PrescriptionEventType;

@Service
public class PrescriptionEventPublisher {

    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    public PrescriptionEventPublisher(
            KafkaTemplate<String, byte[]> kafkaTemplate) {

        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(
            Prescription prescription,
            PrescriptionEventType eventType) {

        PrescriptionEvent event = PrescriptionEvent.newBuilder()
                .setPrescriptionId(prescription.getId())
                .setPatientId(prescription.getPatientId().toString())
                .setDoctorId(prescription.getDoctorId())
                .setDiagnosis(prescription.getDiagnosis())
                .addAllMedicines(prescription.getMedicines())
                .setConsultationFee(prescription.getConsultationFee())
                .setPrescribedAt(prescription.getPrescribedAt().toString())
                .setNotes(
                        prescription.getNotes() == null
                                ? ""
                                : prescription.getNotes())
                .setBillId(
                        prescription.getBillId() == null
                                ? ""
                                : prescription.getBillId().toString())
                .setEventType(eventType)
                .setOccurredAt(System.currentTimeMillis())
                .build();

        kafkaTemplate.send(
                "prescription-events",
                prescription.getId(),
                event.toByteArray());
    }
}