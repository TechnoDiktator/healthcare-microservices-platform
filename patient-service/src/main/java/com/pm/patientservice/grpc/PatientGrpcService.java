package com.pm.patientservice.grpc;

import com.pm.patientservice.model.Patient;
import com.pm.patientservice.repository.PatientRepository;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@GrpcService
public class PatientGrpcService extends PatientServiceGrpc.PatientServiceImplBase {
    private final PatientRepository patientRepository;

    public PatientGrpcService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }
    @Override
    public void getPatientById(
            GetPatientRequest request,
            StreamObserver<PatientResponse> responseObserver) {

        try {

            UUID patientId = UUID.fromString(request.getPatientId());

            Patient patient = patientRepository.findById(patientId)
                    .orElse(null);

            if (patient == null) {
                responseObserver.onError(
                        Status.NOT_FOUND
                                .withDescription("Patient not found with id: " + patientId)
                                .asRuntimeException());
                return;
            }

            PatientResponse response = PatientResponse.newBuilder()
                    .setId(patient.getId().toString())
                    .setName(patient.getName())
                    .setEmail(patient.getEmail())
                    .setAddress(patient.getAddress())
                    .setDateOfBirth(patient.getDateOfBirth().toString())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (IllegalArgumentException e) {

            responseObserver.onError(
                    Status.INVALID_ARGUMENT
                            .withDescription("Invalid patient UUID")
                            .asRuntimeException());

        } catch (Exception e) {

            responseObserver.onError(
                    Status.INTERNAL
                            .withDescription("Failed to fetch patient")
                            .withCause(e)
                            .asRuntimeException());
        }
    }

    @Override
    public void getRandomPatient(
            Empty request,
            StreamObserver<PatientResponse> responseObserver) {

        try {

            List<Patient> patients = patientRepository.findAll();

            if (patients.isEmpty()) {
                responseObserver.onError(
                        Status.NOT_FOUND
                                .withDescription("No patients found")
                                .asRuntimeException());
                return;
            }

            Patient patient = patients.get(
                    ThreadLocalRandom.current().nextInt(patients.size()));

            PatientResponse response = PatientResponse.newBuilder()
                    .setId(patient.getId().toString())
                    .setName(patient.getName())
                    .setEmail(patient.getEmail())
                    .setAddress(patient.getAddress())
                    .setDateOfBirth(patient.getDateOfBirth().toString())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (Exception e) {

            responseObserver.onError(
                    Status.INTERNAL
                            .withDescription("Failed to fetch random patient")
                            .withCause(e)
                            .asRuntimeException());
        }
    }

}