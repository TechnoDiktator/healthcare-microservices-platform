package com.pm.doctorservice.config;

import com.pm.doctorservice.model.Doctor;
import com.pm.doctorservice.repository.DoctorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DoctorDataSeeder {

    @Bean
    CommandLineRunner seedDoctors(DoctorRepository repository) {

        return args -> {

            if (repository.count() > 0) {
                return;
            }

            repository.save(createDoctor(
                    "Rahul",
                    "Sharma",
                    "rahul@example.com",
                    "General Physician",
                    "9876543210",
                    "MBBS",
                    8
            ));

            repository.save(createDoctor(
                    "Priya",
                    "Verma",
                    "priya@example.com",
                    "Neurologist",
                    "9876543211",
                    "MD Neurology",
                    10
            ));

            repository.save(createDoctor(
                    "Amit",
                    "Singh",
                    "amit@example.com",
                    "Orthopedic",
                    "9876543212",
                    "MS Orthopedics",
                    12
            ));

            repository.save(createDoctor(
                    "Neha",
                    "Gupta",
                    "neha@example.com",
                    "General Physician",
                    "9876543213",
                    "MBBS",
                    5
            ));
        };
    }

    private Doctor createDoctor(
            String firstName,
            String lastName,
            String email,
            String specialization,
            String phone,
            String qualification,
            Integer experience) {

        Doctor doctor = new Doctor();

        doctor.setFirstName(firstName);
        doctor.setLastName(lastName);
        doctor.setEmail(email);
        doctor.setSpecialization(specialization);
        doctor.setPhoneNumber(phone);
        doctor.setQualification(qualification);
        doctor.setExperience(experience);
        doctor.setAvailable(true);

        return doctor;
    }
}