package com.pm.doctorservice.repository;

import com.pm.doctorservice.model.Doctor;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends MongoRepository<Doctor, String> {

    Optional<Doctor> findByEmail(String email);

    List<Doctor> findBySpecialization(String specialization);
}