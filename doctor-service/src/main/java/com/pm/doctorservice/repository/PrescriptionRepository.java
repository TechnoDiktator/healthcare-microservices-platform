package com.pm.doctorservice.repository;


import com.pm.doctorservice.model.Prescription;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface PrescriptionRepository
        extends MongoRepository<Prescription, String> {

    List<Prescription> findByPatientId(UUID patientId);

    List<Prescription> findByDoctorId(String doctorId);

    List<Prescription> findByPatientIdAndDoctorId(
            UUID patientId,
            UUID doctorId);
}