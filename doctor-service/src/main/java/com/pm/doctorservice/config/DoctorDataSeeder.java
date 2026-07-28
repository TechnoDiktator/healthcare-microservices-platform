package com.pm.doctorservice.config;

import com.pm.doctorservice.enums.Specialization;
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
                    "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa",
                    "Rahul",
                    "Sharma",
                    "rahul.sharma@example.com",
                    Specialization.GENERAL_PHYSICIAN,
                    "9876543210",
                    "MBBS",
                    8
            ));

            repository.save(createDoctor(
                    "bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb",
                    "Priya",
                    "Verma",
                    "priya.verma@example.com",
                    Specialization.CARDIOLOGIST,
                    "9876543211",
                    "DM Cardiology",
                    14
            ));

            repository.save(createDoctor(
                    "cccccccc-cccc-cccc-cccc-cccccccccccc",
                    "Amit",
                    "Singh",
                    "amit.singh@example.com",
                    Specialization.NEUROLOGIST,
                    "9876543212",
                    "DM Neurology",
                    11
            ));

            repository.save(createDoctor(
                    "dddddddd-dddd-dddd-dddd-dddddddddddd",
                    "Neha",
                    "Gupta",
                    "neha.gupta@example.com",
                    Specialization.ORTHOPEDIC,
                    "9876543213",
                    "MS Orthopedics",
                    9
            ));

            repository.save(createDoctor(
                    "eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee",
                    "Vikram",
                    "Mehta",
                    "vikram.mehta@example.com",
                    Specialization.DERMATOLOGIST,
                    "9876543214",
                    "MD Dermatology",
                    10
            ));

            repository.save(createDoctor(
                    "ffffffff-ffff-ffff-ffff-ffffffffffff",
                    "Anjali",
                    "Patel",
                    "anjali.patel@example.com",
                    Specialization.PEDIATRICIAN,
                    "9876543215",
                    "MD Pediatrics",
                    7
            ));

            repository.save(createDoctor(
                    "11111111-aaaa-bbbb-cccc-111111111111",
                    "Rohan",
                    "Kapoor",
                    "rohan.kapoor@example.com",
                    Specialization.GYNECOLOGIST,
                    "9876543216",
                    "MS Obstetrics & Gynecology",
                    13
            ));

            repository.save(createDoctor(
                    "22222222-bbbb-cccc-dddd-222222222222",
                    "Sneha",
                    "Iyer",
                    "sneha.iyer@example.com",
                    Specialization.OPHTHALMOLOGIST,
                    "9876543217",
                    "MS Ophthalmology",
                    12
            ));

            repository.save(createDoctor(
                    "33333333-cccc-dddd-eeee-333333333333",
                    "Arjun",
                    "Nair",
                    "arjun.nair@example.com",
                    Specialization.ENT_SPECIALIST,
                    "9876543218",
                    "MS ENT",
                    8
            ));

            repository.save(createDoctor(
                    "44444444-dddd-eeee-ffff-444444444444",
                    "Kavya",
                    "Reddy",
                    "kavya.reddy@example.com",
                    Specialization.PSYCHIATRIST,
                    "9876543219",
                    "MD Psychiatry",
                    15
            ));
        };
    }

    private Doctor createDoctor(
            String id,
            String firstName,
            String lastName,
            String email,
            Specialization specialization,
            String phone,
            String qualification,
            Integer experience) {

        Doctor doctor = new Doctor();

        doctor.setId(id);
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