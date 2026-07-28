package com.pm.doctorservice.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import billing.GenerateBillResponse;
import com.pm.doctorservice.dto.PrescriptionRequestDTO;
import com.pm.doctorservice.dto.PrescriptionResponseDTO;
import com.pm.doctorservice.enums.Specialization;
import com.pm.doctorservice.exception.DoctorNotFoundException;
import com.pm.doctorservice.exception.InvalidDoctorSpecializationException;
import com.pm.doctorservice.exception.PatientNotFoundException;
import com.pm.doctorservice.exception.PrescriptionNotFoundException;
import com.pm.doctorservice.grpc.BillingServiceGrpcClient;
import com.pm.doctorservice.grpc.PatientServiceGrpcClient;
import com.pm.doctorservice.mapper.DiseaseMapper;
import com.pm.doctorservice.mapper.PrescriptionMapper;
import com.pm.doctorservice.model.Doctor;
import com.pm.doctorservice.model.Prescription;
import com.pm.doctorservice.repository.DoctorRepository;
import com.pm.doctorservice.repository.PrescriptionRepository;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {
    private static final Logger log =
            LoggerFactory.getLogger(PrescriptionServiceImpl.class);
    private final PrescriptionRepository prescriptionRepository;
    private final DoctorRepository doctorRepository;
    private final BillingServiceGrpcClient billingClient;
    private final PatientServiceGrpcClient patientServiceGrpcClient;
    private final DiseaseMapper diseaseMapper;


    public PrescriptionServiceImpl(
            PrescriptionRepository prescriptionRepository,
            DoctorRepository doctorRepository,
            BillingServiceGrpcClient billingClient,
            PatientServiceGrpcClient patientServiceGrpcClient,
            DiseaseMapper diseaseMapper
    ) {

        this.prescriptionRepository = prescriptionRepository;
        this.doctorRepository = doctorRepository;
        this.billingClient = billingClient;
        this.patientServiceGrpcClient = patientServiceGrpcClient;
        this.diseaseMapper = diseaseMapper;
    }

    @Override
    public PrescriptionResponseDTO createPrescription(
            String doctorId,
            PrescriptionRequestDTO request) {

        log.info("Creating prescription. DoctorId={}, PatientId={}, Diagnosis={}",
                doctorId,
                request.getPatientId(),
                request.getDiagnosis());

        // Verify doctor exists
        log.info("Looking up doctor...");

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() ->
                        new DoctorNotFoundException(
                                "Doctor not found with id: " + doctorId));

        log.info("Doctor found. Specialization={}", doctor.getSpecialization());

        // Verify patient exists
        log.info("Calling Patient Service via gRPC...");

        try {
            patientServiceGrpcClient.getPatientById(
                    request.getPatientId().toString());

            log.info("Patient verified successfully.");

        } catch (StatusRuntimeException e) {

            log.error("Patient Service gRPC failed. Status={}, Description={}",
                    e.getStatus().getCode(),
                    e.getStatus().getDescription(),
                    e);

            if (e.getStatus().getCode() == Status.Code.NOT_FOUND) {
                throw new PatientNotFoundException(
                        "Patient not found with id: "
                                + request.getPatientId());
            }

            throw e;
        }

        log.info("Checking doctor specialization...");

        Specialization expectedSpecialization =
                diseaseMapper.getSpecialization(
                        request.getDiagnosis());

        log.info("Expected={}, Actual={}",
                expectedSpecialization,
                doctor.getSpecialization());

        if (doctor.getSpecialization() != expectedSpecialization) {
            throw new InvalidDoctorSpecializationException(
                    String.format(
                            "Diagnosis '%s' requires a %s, but the selected doctor specializes in %s.",
                            request.getDiagnosis(),
                            expectedSpecialization,
                            doctor.getSpecialization()));
        }

        log.info("Saving prescription...");

        Prescription prescription =
                PrescriptionMapper.toEntity(
                        doctorId,
                        request);

        prescription.setPrescribedAt(LocalDateTime.now());

        prescription = prescriptionRepository.save(prescription);

        log.info("Prescription saved. Id={}", prescription.getId());

        log.info("Calling Billing Service via gRPC...");

        GenerateBillResponse billResponse =
                billingClient.generateBill(
                        prescription.getPatientId().toString(),
                        doctor.getId(),
                        prescription.getConsultationFee());

        log.info("Billing Service responded. BillId={}",
                billResponse.getBillId());

        prescription.setBillId(
                UUID.fromString(billResponse.getBillId()));

        prescription = prescriptionRepository.save(prescription);

        log.info("Prescription completed successfully.");

        return PrescriptionMapper.toDTO(prescription);
    }



    @Override
    public PrescriptionResponseDTO getPrescriptionById(
            String prescriptionId) {

        Prescription prescription = prescriptionRepository.findById(prescriptionId)
                .orElseThrow(() ->
                        new PrescriptionNotFoundException(
                                "Prescription not found with id: " + prescriptionId));

        return PrescriptionMapper.toDTO(prescription);
    }






    @Override
    public List<PrescriptionResponseDTO> getPrescriptionsByDoctor(
            UUID doctorId) {

        return prescriptionRepository.findByDoctorId(doctorId)
                .stream()
                .map(PrescriptionMapper::toDTO)
                .toList();
    }

    @Override
    public List<PrescriptionResponseDTO> getPrescriptionsByPatient(
            UUID patientId) {

        return prescriptionRepository.findByPatientId(patientId)
                .stream()
                .map(PrescriptionMapper::toDTO)
                .toList();
    }

    @Override
    public void deletePrescription(String prescriptionId) {

        if (!prescriptionRepository.existsById(prescriptionId)) {
            throw new PrescriptionNotFoundException(
                    "Prescription not found with id: " + prescriptionId);
        }

        prescriptionRepository.deleteById(prescriptionId);
    }
}