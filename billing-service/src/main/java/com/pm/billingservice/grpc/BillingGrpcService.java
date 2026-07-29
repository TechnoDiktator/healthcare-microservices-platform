package com.pm.billingservice.grpc;

import billing.*;
import com.pm.billingservice.exception.BillAlreadyPaidException;
import com.pm.billingservice.exception.BillNotFoundException;
import com.pm.billingservice.mapper.BillingMapper;
import com.pm.billingservice.model.Bill;
import com.pm.billingservice.service.BillingService;
import io.grpc.Status;
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

        try {

            log.info("GenerateBill request received");

            Bill bill = billingService.generateBill(
                    BillingMapper.toCreateBillRequest(request));

            responseObserver.onNext(
                    BillingMapper.toGenerateBillResponse(bill));

            responseObserver.onCompleted();

        } catch (IllegalArgumentException ex) {

            responseObserver.onError(
                    Status.INVALID_ARGUMENT
                            .withDescription(ex.getMessage())
                            .asRuntimeException());

        } catch (Exception ex) {

            log.error("Failed to generate bill", ex);

            responseObserver.onError(
                    Status.INTERNAL
                            .withDescription("Unable to generate bill.")
                            .asRuntimeException());
        }
    }

    @Override
    public void getBill(
            GetBillRequest request,
            StreamObserver<BillResponse> responseObserver) {

        try {

            log.info("GetBill request received for billId={}",
                    request.getBillId());

            Bill bill = billingService.getBill(
                    UUID.fromString(request.getBillId()));

            responseObserver.onNext(
                    BillingMapper.toBillResponse(bill));

            responseObserver.onCompleted();

        } catch (BillNotFoundException ex) {

            responseObserver.onError(
                    Status.NOT_FOUND
                            .withDescription(ex.getMessage())
                            .asRuntimeException());

        } catch (IllegalArgumentException ex) {

            responseObserver.onError(
                    Status.INVALID_ARGUMENT
                            .withDescription("Invalid bill id.")
                            .asRuntimeException());

        } catch (Exception ex) {

            log.error("Failed to fetch bill", ex);

            responseObserver.onError(
                    Status.INTERNAL
                            .withDescription("Unable to fetch bill.")
                            .asRuntimeException());
        }
    }

    @Override
    public void payBill(
            PayBillRequest request,
            StreamObserver<PaymentResponse> responseObserver) {

        try {

            log.info("PayBill request received for billId={}",
                    request.getBillId());

            Bill bill = billingService.payBill(
                    UUID.fromString(request.getBillId()));

            responseObserver.onNext(
                    BillingMapper.toPaymentResponse(bill));

            responseObserver.onCompleted();

        } catch (BillNotFoundException ex) {

            responseObserver.onError(
                    Status.NOT_FOUND
                            .withDescription(ex.getMessage())
                            .asRuntimeException());

        } catch (BillAlreadyPaidException ex) {

            responseObserver.onError(
                    Status.FAILED_PRECONDITION
                            .withDescription(ex.getMessage())
                            .asRuntimeException());

        } catch (IllegalArgumentException ex) {

            responseObserver.onError(
                    Status.INVALID_ARGUMENT
                            .withDescription("Invalid bill id.")
                            .asRuntimeException());

        } catch (Exception ex) {

            log.error("Failed to pay bill", ex);

            responseObserver.onError(
                    Status.INTERNAL
                            .withDescription("Unable to process payment.")
                            .asRuntimeException());
        }
    }



    @Override
    public void getAllBills(
            GetAllBillsRequest request,
            StreamObserver<GetAllBillsResponse> responseObserver) {

        try {

            log.info("GetAllBills request received");

            GetAllBillsResponse response =
                    GetAllBillsResponse.newBuilder()
                            .addAllBills(
                                    billingService.getAllBills()
                                            .stream()
                                            .map(BillingMapper::toBillResponse)
                                            .toList())
                            .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (Exception ex) {

            log.error("Failed to fetch all bills", ex);

            responseObserver.onError(
                    Status.INTERNAL
                            .withDescription("Unable to fetch bills.")
                            .asRuntimeException());
        }
    }



}