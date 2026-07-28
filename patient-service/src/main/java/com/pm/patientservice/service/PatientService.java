package com.pm.patientservice.service;

import com.pm.patientservice.dto.DoctorResponseDTO;
import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.enums.Specialization;
import com.pm.patientservice.exception.EmailAlreadyExistsException;
import com.pm.patientservice.exception.PatientNotFoundException;
import com.pm.patientservice.grpc.DoctorServiceGrpcClient;
import com.pm.patientservice.kafka.KafkaProducer;
import com.pm.patientservice.mapper.DiseaseMapper;
import com.pm.patientservice.mapper.PatientMapper;
import com.pm.patientservice.model.Patient;
import com.pm.patientservice.repository.PatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class PatientService implements IPatientService {

    private final PatientRepository patientRepository;
    private final KafkaProducer kafkaProducer;
    private final DoctorServiceGrpcClient doctorClient;
    private final DiseaseMapper diseaseMapper;
    private static final Logger log = LoggerFactory.getLogger(PatientService.class);
    public PatientService(
            PatientRepository patientRepository,
            KafkaProducer kafkaProducer,
            DoctorServiceGrpcClient doctorClient,
            DiseaseMapper diseaseMapper) {

        this.patientRepository = patientRepository;
        this.kafkaProducer = kafkaProducer;
        this.doctorClient = doctorClient;
        this.diseaseMapper = diseaseMapper;
    }

    public List<PatientResponseDTO> getPatients() {

        return patientRepository.findAll()
                .stream()
                .map(PatientMapper::toDTO)
                .toList();
    }

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {

        if (patientRepository.existsByEmail(patientRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException(
                    "A patient with email " +
                            patientRequestDTO.getEmail() +
                            " already exists."
            );
        }

        Patient newPatient =
                patientRepository.save(
                        PatientMapper.toPatientModel(patientRequestDTO));

        kafkaProducer.sendEvent(newPatient);

        return PatientMapper.toDTO(newPatient);
    }

    public PatientResponseDTO updatePatient(
            UUID id,
            PatientRequestDTO patientRequestDTO) {

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() ->
                        new PatientNotFoundException(
                                "Patient not found with id: " + id));

        if (patientRepository.existsByEmailAndIdNot(
                patientRequestDTO.getEmail(), id)) {

            throw new EmailAlreadyExistsException(
                    "A patient with email " +
                            patientRequestDTO.getEmail() +
                            " already exists."
            );
        }

        patient.setName(patientRequestDTO.getName());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setDateOfBirth(
                LocalDate.parse(patientRequestDTO.getDateOfBirth()));

        Patient updatedPatient = patientRepository.save(patient);

        return PatientMapper.toDTO(updatedPatient);
    }

    public void deletePatient(UUID id) {

        if (!patientRepository.existsById(id)) {
            throw new PatientNotFoundException(
                    "Patient not found with id: " + id);
        }

        patientRepository.deleteById(id);
    }

    public PatientResponseDTO getPatientById(UUID id) {

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() ->
                        new PatientNotFoundException(
                                "Patient not found with id: " + id));

        return PatientMapper.toDTO(patient);
    }

    @Override
    public List<DoctorResponseDTO> getRecommendedDoctors(
            UUID patientId,
            String disease) {

        log.info("Fetching recommended doctors for patient {} with disease {}",
                patientId, disease);

        patientRepository.findById(patientId)
                .orElseThrow(() ->
                        new PatientNotFoundException(
                                "Patient not found with id: " + patientId));

        Specialization specialization = diseaseMapper.getSpecialization(disease);

        log.info("Mapped disease {} to specialization {}",
                disease, specialization);

        return doctorClient.getDoctorsBySpecialization(specialization);
    }

    @Override
    public PatientResponseDTO getRandomPatient() {

        List<Patient> patients = patientRepository.findAll();

        if (patients.isEmpty()) {
            throw new PatientNotFoundException("No patients found.");
        }

        Patient patient = patients.get(
                ThreadLocalRandom.current().nextInt(patients.size()));

        return PatientMapper.toDTO(patient);
    }
}