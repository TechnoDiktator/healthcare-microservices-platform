package com.pm.doctorservice.service.impl;

import com.pm.doctorservice.dto.DoctorRequestDTO;
import com.pm.doctorservice.dto.DoctorResponseDTO;
import com.pm.doctorservice.exception.DoctorNotFoundException;
import com.pm.doctorservice.exception.EmailAlreadyExistsException;
import com.pm.doctorservice.mapper.DoctorMapper;
import com.pm.doctorservice.model.Doctor;
import com.pm.doctorservice.repository.DoctorRepository;
import com.pm.doctorservice.service.DoctorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorServiceImpl(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public DoctorResponseDTO createDoctor(DoctorRequestDTO requestDTO) {

        doctorRepository.findByEmail(requestDTO.getEmail())
                .ifPresent(doctor -> {
                    throw new EmailAlreadyExistsException(
                            "Doctor with email " + requestDTO.getEmail() + " already exists");
                });

        Doctor doctor = DoctorMapper.toDoctor(requestDTO);

        Doctor savedDoctor = doctorRepository.save(doctor);

        return DoctorMapper.toDoctorResponseDTO(savedDoctor);
    }

    @Override
    public DoctorResponseDTO getDoctorById(String id) {

        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() ->
                        new DoctorNotFoundException("Doctor not found with id: " + id));

        return DoctorMapper.toDoctorResponseDTO(doctor);
    }

    @Override
    public List<DoctorResponseDTO> getAllDoctors() {

        return doctorRepository.findAll()
                .stream()
                .map(DoctorMapper::toDoctorResponseDTO)
                .toList();
    }

    @Override
    public DoctorResponseDTO updateDoctor(String id,
                                          DoctorRequestDTO requestDTO) {

        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() ->
                        new DoctorNotFoundException("Doctor not found with id: " + id));

        doctorRepository.findByEmail(requestDTO.getEmail())
                .ifPresent(existingDoctor -> {
                    if (!existingDoctor.getId().equals(id)) {
                        throw new EmailAlreadyExistsException(
                                "Doctor with email " + requestDTO.getEmail() + " already exists");
                    }
                });

        doctor.setFirstName(requestDTO.getFirstName());
        doctor.setLastName(requestDTO.getLastName());
        doctor.setEmail(requestDTO.getEmail());
        doctor.setPhoneNumber(requestDTO.getPhoneNumber());
        doctor.setSpecialization(requestDTO.getSpecialization());
        doctor.setQualification(requestDTO.getQualification());
        doctor.setExperience(requestDTO.getExperience());

        Doctor updatedDoctor = doctorRepository.save(doctor);

        return DoctorMapper.toDoctorResponseDTO(updatedDoctor);
    }

    @Override
    public void deleteDoctor(String id) {

        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() ->
                        new DoctorNotFoundException("Doctor not found with id: " + id));

        doctorRepository.delete(doctor);
    }

    @Override
    public List<DoctorResponseDTO> getDoctorsBySpecialization(String specialization) {

        return doctorRepository.findBySpecialization(specialization)
                .stream()
                .map(DoctorMapper::toDoctorResponseDTO)
                .toList();
    }
}