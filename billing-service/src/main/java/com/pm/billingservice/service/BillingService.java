package com.pm.billingservice.service;

import billing.GenerateBillRequest;
import com.pm.billingservice.dto.CreateBillRequest;
import com.pm.billingservice.model.Bill;

import java.util.List;
import java.util.UUID;

public interface BillingService {

    Bill generateBill(CreateBillRequest request);

    Bill getBill(UUID billId);

    Bill payBill(UUID billId);
    List<Bill> getAllBills();
}