package com.pm.billingservice.grpc;


import billing.BillResponse;
import billing.BillingServiceGrpc;
import billing.GenerateBillRequest;
import billing.GenerateBillResponse;
import billing.GetBillRequest;
import billing.PayBillRequest;
import billing.PaymentResponse;
import com.pm.billingservice.mapper.BillingMapper;
import com.pm.billingservice.model.Bill;
import com.pm.billingservice.service.BillingService;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

@GrpcService
public class BillingGrpcService extends BillingServiceGrpc.BillingServiceImplBase {


    private static final Logger log =
            LoggerFactory.getLogger(BillingGrpcService.class);

    private final BillingService billingService;

    public BillingGrpcService(BillingService billingService) {
        this.billingService = billingService;
    }

    @Override
    public void generateBill(
            GenerateBillRequest request,
            StreamObserver<GenerateBillResponse> responseObserver) {

        log.info("GenerateBill request received: {}", request);

        Bill bill = billingService.generateBill(BillingMapper.toCreateBillRequest(request));

        responseObserver.onNext(
                BillingMapper.toGenerateBillResponse(bill));

        responseObserver.onCompleted();
    }

    @Override
    public void getBill(
            GetBillRequest request,
            StreamObserver<BillResponse> responseObserver) {

        log.info("GetBill request received: {}", request);

        Bill bill = billingService.getBill(
                UUID.fromString(request.getBillId()));

        responseObserver.onNext(
                BillingMapper.toBillResponse(bill));

        responseObserver.onCompleted();
    }

    @Override
    public void payBill(
            PayBillRequest request,
            StreamObserver<PaymentResponse> responseObserver) {

        log.info("PayBill request received: {}", request);

        Bill bill = billingService.payBill(
                UUID.fromString(request.getBillId()));

        responseObserver.onNext(
                BillingMapper.toPaymentResponse(bill));

        responseObserver.onCompleted();
    }
}