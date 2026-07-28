package com.pm.patientservice.grpc;

import com.pm.patientservice.dto.DoctorResponseDTO;
import doctor.DoctorListResponse;
import doctor.DoctorResponse;
import doctor.DoctorServicesGrpc;
import doctor.DoctorSpecializationRequest;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.pm.patientservice.enums.Specialization;
import java.util.List;



@Service
public class DoctorServiceGrpcClient {
    private final ManagedChannel channel;
    private static final Logger log =
            LoggerFactory.getLogger(DoctorServiceGrpcClient.class);

    private final DoctorServicesGrpc.DoctorServicesBlockingStub blockingStub;

    public DoctorServiceGrpcClient(
            @Value("${doctor.service.address:localhost}") String serverAddress,
            @Value("${doctor.service.grpc.port:9002}") int serverPort) {

        log.info("Connecting to Doctor GRPC Service at {}:{}",
                serverAddress,
                serverPort);

        channel =
                ManagedChannelBuilder
                        .forAddress(serverAddress, serverPort)
                        .usePlaintext()
                        .build();

        blockingStub = DoctorServicesGrpc.newBlockingStub(channel);
    }

    public List<DoctorResponseDTO> getDoctorsBySpecialization(
            Specialization specialization){

        DoctorSpecializationRequest request =
                DoctorSpecializationRequest.newBuilder()
                        .setSpecialization(
                                doctor.Specialization.valueOf(
                                        specialization.name()
                                )
                        )
                        .build();

        DoctorListResponse response =
                blockingStub.getDoctorsBySpecialization(request);

        return response.getDoctorsList()
                .stream()
                .map(this::toDoctorDTO)
                .toList();
    }

    private DoctorResponseDTO toDoctorDTO(DoctorResponse doctor) {

        return new DoctorResponseDTO(
                doctor.getId(),
                doctor.getFirstName(),
                doctor.getLastName(),
                doctor.getEmail(),
                Specialization.valueOf(
                        doctor.getSpecialization().name()
                ),
                doctor.getPhoneNumber(),
                doctor.getQualification(),
                doctor.getExperience()
        );
    }
    @PreDestroy
    public void shutdown() {
        channel.shutdown();
    }
}