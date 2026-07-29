package com.pm.patientservice.grpc;

import billing.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BillingGrpcClient {

    private final BillingServiceGrpc.BillingServiceBlockingStub billingStub;

    public BillingGrpcClient(
            @Value("${billing.service.address}") String host,
            @Value("${billing.service.grpc.port}") int port) {

        ManagedChannel channel =
                ManagedChannelBuilder
                        .forAddress(host, port)
                        .usePlaintext()
                        .build();

        this.billingStub =
                BillingServiceGrpc.newBlockingStub(channel);
    }

    public BillResponse getBill(String billId) {

        GetBillRequest request =
                GetBillRequest.newBuilder()
                        .setBillId(billId)
                        .build();

        return billingStub.getBill(request);
    }

    public PaymentResponse payBill(String billId) {

        PayBillRequest request =
                PayBillRequest.newBuilder()
                        .setBillId(billId)
                        .build();

        return billingStub.payBill(request);
    }


    public GetAllBillsResponse getAllBills() {

        GetAllBillsRequest request =
                GetAllBillsRequest.newBuilder()
                        .build();

        return billingStub.getAllBills(request);
    }


}