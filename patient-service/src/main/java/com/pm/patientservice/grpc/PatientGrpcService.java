package com.pm.patientservice.grpc;

import com.pm.patientservice.model.Patient;
import com.pm.patientservice.repository.PatientRepository;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@GrpcService
public class PatientGrpcService extends PatientServiceGrpc.PatientServiceImplBase {
    private final PatientRepository patientRepository;
    private static final Logger log =
            LoggerFactory.getLogger(PatientGrpcService.class);
    public PatientGrpcService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }
    @Override



    public void getPatientById(
            GetPatientRequest request,
            StreamObserver<PatientResponse> responseObserver) {

        try {

            UUID patientId = UUID.fromString(request.getPatientId());

            log.info("Received gRPC request for patientId={}", patientId);

            long count = patientRepository.count();
            log.info("Total patients in database={}", count);

            patientRepository.findAll().forEach(patient ->
                    log.info("Patient in DB: {}", patient.getId()));

            Patient patient = patientRepository.findById(patientId)
                    .orElse(null);

            if (patient == null) {
                log.warn("Patient not found. Requested patientId={}", patientId);

                responseObserver.onError(
                        Status.NOT_FOUND
                                .withDescription("Patient not found with id: " + patientId)
                                .asRuntimeException());
                return;
            }

            log.info("Patient found. Id={}, Name={}",
                    patient.getId(),
                    patient.getName());

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