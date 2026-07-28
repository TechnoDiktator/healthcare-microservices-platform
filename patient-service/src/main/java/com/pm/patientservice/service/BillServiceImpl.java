package com.pm.patientservice.service;

import billing.BillResponse;
import billing.PaymentResponse;
import com.pm.patientservice.dto.BillResponseDTO;
import com.pm.patientservice.dto.PaymentResponseDTO;
import com.pm.patientservice.grpc.BillingGrpcClient;
import com.pm.patientservice.mapper.BillMapper;
import org.springframework.stereotype.Service;

@Service
public class BillServiceImpl implements BillService {

    private final BillingGrpcClient billingGrpcClient;
    private final BillMapper billMapper;

    public BillServiceImpl(
            BillingGrpcClient billingGrpcClient,
            BillMapper billMapper) {

        this.billingGrpcClient = billingGrpcClient;
        this.billMapper = billMapper;
    }

    @Override
    public BillResponseDTO getBill(
            String patientId,
            String billId) {

        BillResponse response =
                billingGrpcClient.getBill(billId);

        return billMapper.toDTO(response);
    }

    @Override
    public PaymentResponseDTO payBill(
            String patientId,
            String billId) {

        PaymentResponse response =
                billingGrpcClient.payBill(billId);

        return billMapper.toDTO(response);
    }
}