package com.pm.patientservice.dto;

public class DoctorResponseDTO {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String specialization;
    private String phoneNumber;
    private String qualification;
    private Integer experience;

    public DoctorResponseDTO() {
    }

    public DoctorResponseDTO(String id,
                             String firstName,
                             String lastName,
                             String email,
                             String specialization,
                             String phoneNumber,
                             String qualification,
                             Integer experience) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.specialization = specialization;
        this.phoneNumber = phoneNumber;
        this.qualification = qualification;
        this.experience = experience;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }
}