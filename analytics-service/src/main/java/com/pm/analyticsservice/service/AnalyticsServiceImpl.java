package com.pm.analyticsservice.service;

import billing.events.BillingEvent;
import com.pm.analyticsservice.document.*;
import com.pm.analyticsservice.repository.*;
import com.pm.analyticsservice.service.AnalyticsService;
import doctor.events.DoctorEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;
import prescription.events.PrescriptionEvent;

import java.util.List;

@Service
public class AnalyticsServiceImpl implements AnalyticsService {

    private static final Logger log =
            LoggerFactory.getLogger(AnalyticsServiceImpl.class);

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

        log.info("Processing PatientEvent: {}", event.getPatientId());

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

        log.info("Saving PatientProjection to MongoDB: {}", patient);

        try {

            patientRepository.save(patient);

            log.info("Successfully saved PatientProjection with id={}",
                    patient.getPatientId());

        } catch (Exception e) {

            log.error("Failed to save PatientProjection with id={}",
                    patient.getPatientId(), e);

            throw e;
        }
    }

    @Override
    public void handleDoctorEvent(DoctorEvent event) {

        log.info("Processing DoctorEvent: {}", event.getDoctorId());

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

        log.info("Saving DoctorProjection to MongoDB: {}", doctor);

        try {

            doctorRepository.save(doctor);

            log.info("Successfully saved DoctorProjection with id={}",
                    doctor.getDoctorId());

        } catch (Exception e) {

            log.error("Failed to save DoctorProjection with id={}",
                    doctor.getDoctorId(), e);

            throw e;
        }
    }

    @Override
    public void handleBillingEvent(BillingEvent event) {

        log.info("Processing BillingEvent: {}", event.getBillId());

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

        log.info("Saving BillingProjection to MongoDB: {}", billing);

        try {

            billingRepository.save(billing);

            log.info("Successfully saved BillingProjection with id={}",
                    billing.getBillId());

        } catch (Exception e) {

            log.error("Failed to save BillingProjection with id={}",
                    billing.getBillId(), e);

            throw e;
        }
    }

    @Override
    public void handlePrescriptionEvent(PrescriptionEvent event) {

        log.info("Processing PrescriptionEvent: {}", event.getPrescriptionId());

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

        log.info("Saving PrescriptionProjection to MongoDB: {}", prescription);

        try {

            prescriptionRepository.save(prescription);

            log.info("Successfully saved PrescriptionProjection with id={}",
                    prescription.getPrescriptionId());

        } catch (Exception e) {

            log.error("Failed to save PrescriptionProjection with id={}",
                    prescription.getPrescriptionId(), e);

            throw e;
        }
    }

    @Override
    public List<PatientProjection> getAllPatients() {

        log.info("Fetching all patient projections");

        return patientRepository.findAll();
    }

    @Override
    public List<DoctorProjection> getAllDoctors() {

        log.info("Fetching all doctor projections");

        return doctorRepository.findAll();
    }

    @Override
    public List<BillingProjection> getAllBillings() {

        log.info("Fetching all billing projections");

        return billingRepository.findAll();
    }

    @Override
    public List<PrescriptionProjection> getAllPrescriptions() {

        log.info("Fetching all prescription projections");

        return prescriptionRepository.findAll();
    }






}