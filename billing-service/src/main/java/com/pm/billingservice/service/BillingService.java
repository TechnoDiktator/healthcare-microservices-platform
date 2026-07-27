package com.pm.billingservice.service;

import billing.GenerateBillRequest;
import com.pm.billingservice.model.Bill;

import java.util.UUID;

public interface BillingService {

    Bill generateBill(GenerateBillRequest request);

    Bill getBill(UUID billId);

    Bill payBill(UUID billId);

}