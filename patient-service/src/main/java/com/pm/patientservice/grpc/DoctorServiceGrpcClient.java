//package com.pm.patientservice.grpc;
//
//import com.pm.patientservice.dto.DoctorResponseDTO;
//import org.springframework.stereotype.Service;
//import io.grpc.ManagedChannel;
//import io.grpc.ManagedChannelBuilder;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//@Service
//public class DoctorServiceGrpcClient {
//
//    private static final Logger log =
//            LoggerFactory.getLogger(DoctorServiceGrpcClient.class);
//
//    private final DoctorServicesGrpc.DoctorServicesBlockingStub blockingStub;
//
//    public DoctorServiceGrpcClient(
//            @Value("${doctor.service.address:localhost}") String serverAddress,
//            @Value("${doctor.service.grpc.port:9002}") int serverPort) {
//
//        log.info("Connecting to Doctor GRPC Service at {}:{}",
//                serverAddress,
//                serverPort);
//
//        ManagedChannel channel =
//                ManagedChannelBuilder
//                        .forAddress(serverAddress, serverPort)
//                        .usePlaintext()
//                        .build();
//
//        blockingStub = DoctorServicesGrpc.newBlockingStub(channel);
//    }
//
//    public List<DoctorResponseDTO> getDoctorsBySpecialization(
//            String specialization) {
//
//        DoctorSpecializationRequest request =
//                DoctorSpecializationRequest.newBuilder()
//                        .setSpecialization(specialization)
//                        .build();
//
//        DoctorListResponse response =
//                blockingStub.getDoctorsBySpecialization(request);
//
//        return response.getDoctorsList()
//                .stream()
//                .map(this::toDoctorDTO)
//                .toList();
//    }
//
//    private DoctorResponseDTO toDoctorDTO(DoctorResponse doctor) {
//
//        return new DoctorResponseDTO(
//                doctor.getId(),
//                doctor.getFirstName(),
//                doctor.getLastName(),
//                doctor.getEmail(),
//                doctor.getSpecialization(),
//                doctor.getPhoneNumber(),
//                doctor.getQualification(),
//                doctor.getExperience()
//        );
//    }
//}