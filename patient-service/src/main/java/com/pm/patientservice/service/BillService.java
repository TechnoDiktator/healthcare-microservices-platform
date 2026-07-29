package com.pm.patientservice.service;

import com.pm.patientservice.dto.BillResponseDTO;
import com.pm.patientservice.dto.PaymentResponseDTO;

import java.util.List;

public interface BillService {

    BillResponseDTO getBill(
            String patientId,
            String billId);

    PaymentResponseDTO payBill(
            String patientId,
            String billId);


    List<BillResponseDTO> getAllBills();

}