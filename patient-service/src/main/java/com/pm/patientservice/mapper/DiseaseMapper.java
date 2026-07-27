package com.pm.patientservice.mapper;

import org.springframework.stereotype.Component;

@Component
public class DiseaseMapper {

    public String getSpecialization(String disease) {

        return switch (disease.toLowerCase()) {
            case "fever", "cold" -> "General Physician";
            case "migraine" -> "Neurologist";
            case "fracture" -> "Orthopedic";
            default -> "General Physician";
        };
    }
}
