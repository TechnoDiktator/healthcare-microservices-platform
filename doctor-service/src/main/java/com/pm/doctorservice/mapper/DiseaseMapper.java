package com.pm.doctorservice.mapper;

import com.pm.doctorservice.enums.Specialization;
import org.springframework.stereotype.Component;

@Component
public class DiseaseMapper {

    public Specialization getSpecialization(String disease) {

        return switch (disease.toLowerCase().trim()) {

            // General Physician
            case "fever", "cold", "flu", "cough", "viral infection",
                 "body pain", "fatigue", "headache", "infection" ->
                    Specialization.GENERAL_PHYSICIAN;

            // Cardiologist
            case "chest pain", "heart disease", "high blood pressure",
                 "hypertension", "arrhythmia", "heart attack",
                 "palpitations" ->
                    Specialization.CARDIOLOGIST;

            // Neurologist
            case "migraine", "epilepsy", "stroke", "parkinson",
                 "alzheimer", "seizure", "neuropathy",
                 "brain tumor" ->
                    Specialization.NEUROLOGIST;

            // Orthopedic
            case "fracture", "arthritis", "joint pain",
                 "back pain", "bone pain", "sprain",
                 "ligament injury", "osteoporosis" ->
                    Specialization.ORTHOPEDIC;

            // Dermatologist
            case "acne", "eczema", "psoriasis",
                 "skin allergy", "rash", "fungal infection",
                 "hair loss", "vitiligo" ->
                    Specialization.DERMATOLOGIST;

            // Pediatrician
            case "child fever", "newborn care",
                 "vaccination", "child infection",
                 "growth issues" ->
                    Specialization.PEDIATRICIAN;

            // Gynecologist
            case "pregnancy", "pcos", "menstrual pain",
                 "infertility", "ovarian cyst",
                 "uterine fibroids" ->
                    Specialization.GYNECOLOGIST;

            // Ophthalmologist
            case "eye pain", "cataract", "glaucoma",
                 "blurred vision", "vision loss",
                 "conjunctivitis" ->
                    Specialization.OPHTHALMOLOGIST;

            // ENT Specialist
            case "ear infection", "hearing loss",
                 "sinusitis", "tonsillitis",
                 "sore throat", "nose bleeding" ->
                    Specialization.ENT_SPECIALIST;

            // Psychiatrist
            case "depression", "anxiety",
                 "panic attack", "bipolar disorder",
                 "schizophrenia", "insomnia" ->
                    Specialization.PSYCHIATRIST;

            // Urologist
            case "kidney stone", "uti",
                 "urinary infection", "prostate enlargement",
                 "blood in urine", "bladder infection" ->
                    Specialization.UROLOGIST;

            // Oncologist
            case "breast cancer", "lung cancer",
                 "blood cancer", "colon cancer",
                 "tumor", "cancer" ->
                    Specialization.ONCOLOGIST;

            // Endocrinologist
            case "diabetes", "thyroid",
                 "hypothyroidism", "hyperthyroidism",
                 "hormonal imbalance", "obesity" ->
                    Specialization.ENDOCRINOLOGIST;

            // Pulmonologist
            case "asthma", "copd",
                 "tuberculosis", "pneumonia",
                 "lung infection", "shortness of breath" ->
                    Specialization.PULMONOLOGIST;

            // Gastroenterologist
            case "gastritis", "acid reflux",
                 "ulcer", "ibs",
                 "crohn disease", "liver disease",
                 "hepatitis", "constipation" ->
                    Specialization.GASTROENTEROLOGIST;

            // Nephrologist
            case "chronic kidney disease", "kidney failure",
                 "proteinuria", "dialysis",
                 "glomerulonephritis", "nephritis" ->
                    Specialization.NEPHROLOGIST;

            default -> Specialization.GENERAL_PHYSICIAN;
        };
    }
}