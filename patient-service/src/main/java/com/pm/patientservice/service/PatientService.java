package com.pm.patientservice.service;


import com.pm.patientservice.dto.DoctorResponseDTO;
import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.exception.EmailAlreadyExistsException;
import com.pm.patientservice.exception.PatientNotFoundException;
import com.pm.patientservice.grpc.BillingServiceGrpcClient;
import com.pm.patientservice.grpc.DoctorServiceGrpcClient;
import com.pm.patientservice.kafka.KafkaProducer;
import com.pm.patientservice.mapper.DiseaseMapper;
import com.pm.patientservice.mapper.PatientMapper;
import com.pm.patientservice.model.Patient;
import com.pm.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PatientService {

    private final KafkaProducer kafkaProducer;
    private PatientRepository patientRepository;
//    private final BillingServiceGrpcClient billingServiceGrpcClient;

//    private final DoctorServiceGrpcClient doctorClient;

//    private final DiseaseMapper diseaseMapper;


//    public PatientService(
//            PatientRepository patientRepository,
//            BillingServiceGrpcClient billingServiceGrpcClient,
//            KafkaProducer kafkaProducer,
//            DoctorServiceGrpcClient doctorClient,
//            DiseaseMapper diseaseMapper) {
//
//        this.patientRepository = patientRepository;
//        this.billingServiceGrpcClient = billingServiceGrpcClient;
//        this.kafkaProducer = kafkaProducer;
//        this.doctorClient = doctorClient;
//        this.diseaseMapper = diseaseMapper;
//    }

    public List<PatientResponseDTO> getPatients(){

        List<Patient> patients  = patientRepository.findAll();
        List<PatientResponseDTO> patientResponseDTOS = patients.stream().map(
                patient -> PatientMapper.toDTO(patient)).toList();

        return patientResponseDTOS;
    }

    public  PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {

        if(patientRepository.existsByEmail(patientRequestDTO.getEmail())){
            throw new EmailAlreadyExistsException("A patient with this email" + patientRequestDTO.getEmail() + "already exists" );
        }

        Patient newPatient =  patientRepository.save(PatientMapper.toPatientModel(patientRequestDTO));

        billingServiceGrpcClient.createBillingAccount(newPatient.getId().toString() , newPatient.getName() , newPatient.getEmail());

        kafkaProducer.sendEvent(newPatient);

        return PatientMapper.toDTO(newPatient);

    }

    public PatientResponseDTO updatePatient(UUID id , PatientRequestDTO patientRequestDTO){

        Patient patient  = patientRepository.findById(id).orElseThrow(
                () -> new PatientNotFoundException("Patient Not found with id :"+ id)
        );
        if(patientRepository.existsByEmailAndIdNot(   patientRequestDTO.getEmail() , id)){
            throw new EmailAlreadyExistsException("A patient with this email" + patientRequestDTO.getEmail() + "already exists" );
        }

        patient.setName(patientRequestDTO.getName());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));

        Patient updatedPatient = patientRepository.save(patient);

        return  PatientMapper.toDTO(updatedPatient);
    }


    public  void deletePatient(UUID id){
        patientRepository.deleteById(id);
    }

    public List<DoctorResponseDTO> getRecommendedDoctors(
            UUID patientId,
            String disease) {

        patientRepository.findById(patientId)
                .orElseThrow(() ->
                        new PatientNotFoundException(
                                "Patient not found with id: " + patientId));

        String specialization =
                diseaseMapper.getSpecialization(disease);

        return doctorClient.getDoctorsBySpecialization(
                specialization);
    }



//    public PatientResponseDTO














}
