package com.pm.doctorservice.grpc;

import billing.BillingServiceGrpc;
import billing.GenerateBillRequest;
import billing.GenerateBillResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BillingServiceGrpcClient {

    private final ManagedChannel channel;
    private final BillingServiceGrpc.BillingServiceBlockingStub blockingStub;

    public BillingServiceGrpcClient(
            @Value("${billing.service.address:localhost}") String host,
            @Value("${billing.service.grpc.port:9001}") int port) {

        this.channel = ManagedChannelBuilder
                .forAddress(host, port)
                .usePlaintext()
                .build();

        this.blockingStub =
                BillingServiceGrpc.newBlockingStub(channel);
    }

    public GenerateBillResponse generateBill(
            String patientId,
            String doctorId,
            double consultationFee) {

        GenerateBillRequest request =
                GenerateBillRequest.newBuilder()
                        .setPatientId(patientId)
                        .setDoctorId(doctorId)
                        .setConsultationFee(consultationFee)
                        .build();

        return blockingStub.generateBill(request);
    }

    @PreDestroy
    public void shutdown() {
        channel.shutdown();
    }
}

