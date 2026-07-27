package com.pm.doctorservice.service;

import billing.GenerateBillResponse;
import com.pm.doctorservice.dto.PrescriptionRequestDTO;
import com.pm.doctorservice.dto.PrescriptionResponseDTO;
import com.pm.doctorservice.exception.DoctorNotFoundException;
import com.pm.doctorservice.exception.PrescriptionNotFoundException;
import com.pm.doctorservice.grpc.BillingServiceGrpcClient;
import com.pm.doctorservice.mapper.PrescriptionMapper;
import com.pm.doctorservice.model.Doctor;
import com.pm.doctorservice.model.Prescription;
import com.pm.doctorservice.repository.DoctorRepository;
import com.pm.doctorservice.repository.PrescriptionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final DoctorRepository doctorRepository;
    private final BillingServiceGrpcClient billingClient;

    public PrescriptionServiceImpl(
            PrescriptionRepository prescriptionRepository,
            DoctorRepository doctorRepository,
            BillingServiceGrpcClient billingClient) {

        this.prescriptionRepository = prescriptionRepository;
        this.doctorRepository = doctorRepository;
        this.billingClient = billingClient;
    }

    @Override
    public PrescriptionResponseDTO createPrescription(
            UUID doctorId,
            PrescriptionRequestDTO request) {

        Doctor doctor = doctorRepository.findById(String.valueOf(doctorId))
                .orElseThrow(() ->
                        new DoctorNotFoundException(
                                "Doctor not found with id: " + doctorId));

        Prescription prescription =
                PrescriptionMapper.toEntity(doctorId, request);

        prescription.setPrescribedAt(LocalDateTime.now());

        prescription = prescriptionRepository.save(prescription);

        GenerateBillResponse billResponse =
                billingClient.generateBill(
                        prescription.getPatientId().toString(),
                        doctor.getId().toString(),
                        prescription.getConsultationFee());

        prescription.setBillId(
                UUID.fromString(billResponse.getBillId()));

        prescription = prescriptionRepository.save(prescription);

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