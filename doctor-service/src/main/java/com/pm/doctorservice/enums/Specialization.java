package com.pm.doctorservice.enums;

public enum Specialization {

    GENERAL_PHYSICIAN("General Physician"),
    CARDIOLOGIST("Cardiologist"),
    NEUROLOGIST("Neurologist"),
    ORTHOPEDIC("Orthopedic"),
    DERMATOLOGIST("Dermatologist"),
    PEDIATRICIAN("Pediatrician"),
    GYNECOLOGIST("Gynecologist"),
    OPHTHALMOLOGIST("Ophthalmologist"),
    ENT_SPECIALIST("ENT Specialist"),
    PSYCHIATRIST("Psychiatrist"),
    UROLOGIST("Urologist"),
    ONCOLOGIST("Oncologist"),
    ENDOCRINOLOGIST("Endocrinologist"),
    PULMONOLOGIST("Pulmonologist"),
    GASTROENTEROLOGIST("Gastroenterologist"),
    NEPHROLOGIST("Nephrologist");

    private final String displayName;

    Specialization(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
