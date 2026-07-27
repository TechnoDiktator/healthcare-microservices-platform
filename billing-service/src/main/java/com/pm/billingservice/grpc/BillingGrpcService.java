package com.pm.billingservice.grpc;

import billing.BillingServiceGrpc;
import billing.GenerateBillRequest;
import billing.GenerateBillResponse;
import billing.GetBillRequest;
import billing.BillResponse;
import billing.PayBillRequest;
import billing.PaymentResponse;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcService
public class BillingGrpcService extends BillingServiceGrpc.BillingServiceImplBase {

    private static final Logger log =
            LoggerFactory.getLogger(BillingGrpcService.class);

    @Override
    public void generateBill(
            GenerateBillRequest request,
            StreamObserver<GenerateBillResponse> responseObserver) {

        log.info("GenerateBill request received: {}", request);

        // TODO: Delegate to BillingService

        GenerateBillResponse response =
                GenerateBillResponse.newBuilder()
                        .setBillId("bill-123")
                        .setTotalAmount(750.0)
                        .setPaymentStatus("PENDING")
                        .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getBill(
            GetBillRequest request,
            StreamObserver<BillResponse> responseObserver) {

        log.info("GetBill request received: {}", request);

        // TODO: Delegate to BillingService

        BillResponse response =
                BillResponse.newBuilder()
                        .setBillId(request.getBillId())
                        .setPrescriptionId("pres-101")
                        .setPatientId("pat-001")
                        .setDoctorId("doc-001")
                        .setConsultationFee(500)
                        .setMedicineCost(250)
                        .setTotalAmount(750)
                        .setPaymentStatus("PENDING")
                        .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void payBill(
            PayBillRequest request,
            StreamObserver<PaymentResponse> responseObserver) {

        log.info("PayBill request received: {}", request);

        // TODO: Delegate to BillingService

        PaymentResponse response =
                PaymentResponse.newBuilder()
                        .setBillId(request.getBillId())
                        .setPaymentStatus("PAID")
                        .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}