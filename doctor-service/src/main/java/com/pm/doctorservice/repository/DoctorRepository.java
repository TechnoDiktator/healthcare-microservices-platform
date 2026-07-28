package com.pm.doctorservice.repository;

import com.pm.doctorservice.enums.Specialization;
import com.pm.doctorservice.model.Doctor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends MongoRepository<Doctor, String> {

    Optional<Doctor> findByEmail(String email);

    List<Doctor> findBySpecialization(Specialization specialization);
}