package com.pm.analyticsservice.service.impl;

import billing.events.BillingEvent;
import com.pm.analyticsservice.document.*;
import com.pm.analyticsservice.repository.*;
import com.pm.analyticsservice.service.AnalyticsService;
import doctor.events.DoctorEvent;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;
import prescription.events.PrescriptionEvent;

@Service
public class AnalyticsServiceImpl implements AnalyticsService {

    private final PatientProjectionRepository patientRepository;
    private final DoctorProjectionRepository doctorRepository;
    private final BillingProjectionRepository billingRepository;
    private final PrescriptionProjectionRepository prescriptionRepository;

    public AnalyticsServiceImpl(
            PatientProjectionRepository patientRepository,
            DoctorProjectionRepository doctorRepository,
            BillingProjectionRepository billingRepository,
            PrescriptionProjectionRepository prescriptionRepository) {

        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.billingRepository = billingRepository;
        this.prescriptionRepository = prescriptionRepository;
    }

    @Override
    public void handlePatientEvent(PatientEvent event) {

        PatientProjection patient = PatientProjection.builder()
                .patientId(event.getPatientId())
                .name(event.getName())
                .email(event.getEmail())
                .address(event.getAddress())
                .dateOfBirth(event.getDateOfBirth())
                .registeredDate(event.getRegisteredDate())
                .eventType(event.getEventType().name())
                .occurredAt(event.getOccurredAt())
                .build();

        patientRepository.save(patient);
    }

    @Override
    public void handleDoctorEvent(DoctorEvent event) {

        DoctorProjection doctor = DoctorProjection.builder()
                .doctorId(event.getDoctorId())
                .firstName(event.getFirstName())
                .lastName(event.getLastName())
                .email(event.getEmail())
                .specialization(event.getSpecialization())
                .phoneNumber(event.getPhoneNumber())
                .qualification(event.getQualification())
                .experience(event.getExperience())
                .available(event.getAvailable())
                .eventType(event.getEventType().name())
                .occurredAt(event.getOccurredAt())
                .build();

        doctorRepository.save(doctor);
    }

    @Override
    public void handleBillingEvent(BillingEvent event) {

        BillingProjection billing = BillingProjection.builder()
                .billId(event.getBillId())
                .prescriptionId(event.getPrescriptionId())
                .patientId(event.getPatientId())
                .doctorId(event.getDoctorId())
                .consultationFee(event.getConsultationFee())
                .medicineCost(event.getMedicineCost())
                .totalAmount(event.getTotalAmount())
                .paymentStatus(event.getPaymentStatus())
                .createdAt(event.getCreatedAt())
                .eventType(event.getEventType().name())
                .occurredAt(event.getOccurredAt())
                .build();

        billingRepository.save(billing);
    }

    @Override
    public void handlePrescriptionEvent(PrescriptionEvent event) {

        PrescriptionProjection prescription = PrescriptionProjection.builder()
                .prescriptionId(event.getPrescriptionId())
                .patientId(event.getPatientId())
                .doctorId(event.getDoctorId())
                .diagnosis(event.getDiagnosis())
                .medicines(event.getMedicinesList())
                .consultationFee(event.getConsultationFee())
                .prescribedAt(event.getPrescribedAt())
                .notes(event.getNotes())
                .billId(event.getBillId())
                .eventType(event.getEventType().name())
                .occurredAt(event.getOccurredAt())
                .build();

        prescriptionRepository.save(prescription);
    }
}