package com.pm.doctorservice.service;

import com.pm.doctorservice.authclient.AuthClient;
import com.pm.doctorservice.dto.DoctorRequestDTO;
import com.pm.doctorservice.dto.DoctorResponseDTO;
import com.pm.doctorservice.dto.InternalUserRequestDTO;
import com.pm.doctorservice.enums.Role;
import com.pm.doctorservice.enums.Specialization;
import com.pm.doctorservice.exception.DoctorNotFoundException;
import com.pm.doctorservice.exception.EmailAlreadyExistsException;
import com.pm.doctorservice.mapper.DoctorMapper;
import com.pm.doctorservice.model.Doctor;
import com.pm.doctorservice.repository.DoctorRepository;
import com.pm.doctorservice.service.DoctorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final AuthClient authClient;

    public DoctorServiceImpl(DoctorRepository doctorRepository,
                             AuthClient authClient) {
        this.doctorRepository = doctorRepository;
        this.authClient = authClient;
    }





    @Override
    public DoctorResponseDTO createDoctor(DoctorRequestDTO requestDTO) {

        doctorRepository.findByEmail(requestDTO.getEmail())
                .ifPresent(doctor -> {
                    throw new EmailAlreadyExistsException(
                            "Doctor with email " + requestDTO.getEmail() + " already exists");
                });



        Doctor doctor = DoctorMapper.toDoctor(requestDTO);

        UUID doctorId = UUID.randomUUID();
        doctor.setId(doctorId.toString());

        Doctor savedDoctor = doctorRepository.save(doctor);

        InternalUserRequestDTO authRequest = new InternalUserRequestDTO();

        authRequest.setId(doctorId);
        authRequest.setEmail(savedDoctor.getEmail());
        authRequest.setPassword(requestDTO.getPassword());
        authRequest.setRole(Role.DOCTOR);

        try {

            authClient.createInternalUser(authRequest);

        } catch (Exception ex) {

            doctorRepository.delete(savedDoctor);

            throw ex;
        }

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
    public List<DoctorResponseDTO> getDoctorsBySpecialization(
            Specialization specialization) {

        return doctorRepository.findBySpecialization(specialization)
                .stream()
                .map(DoctorMapper::toDoctorResponseDTO)
                .toList();
    }



}