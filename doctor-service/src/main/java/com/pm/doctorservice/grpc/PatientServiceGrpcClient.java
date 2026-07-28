package com.pm.doctorservice.grpc;

import com.pm.patientservice.grpc.GetPatientRequest;
import com.pm.patientservice.grpc.PatientResponse;
import com.pm.patientservice.grpc.PatientServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PatientServiceGrpcClient {

    private final PatientServiceGrpc.PatientServiceBlockingStub blockingStub;

    public PatientServiceGrpcClient(
            @Value("${patient.service.address}") String address,
            @Value("${patient.service.grpc.port}") int port) {

        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(address, port)
                .usePlaintext()
                .build();

        this.blockingStub = PatientServiceGrpc.newBlockingStub(channel);
    }
    public PatientResponse getPatientById(String patientId) {

        GetPatientRequest request =
                GetPatientRequest.newBuilder()
                        .setPatientId(patientId)
                        .build();

        return blockingStub.getPatientById(request);
    }
}