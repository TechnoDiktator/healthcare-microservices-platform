package com.pm.doctorservice.mapper;

import org.springframework.stereotype.Component;

@Component
public class DiseaseMapper {

    public String getSpecialization(String disease) {

        return switch (disease.toLowerCase()) {
            case "fever", "cold" -> "General Physician";
            case "skin allergy" -> "Dermatologist";
            case "fracture" -> "Orthopedic";
            case "migraine" -> "Neurologist";
            default -> "General Physician";
        };
    }
}