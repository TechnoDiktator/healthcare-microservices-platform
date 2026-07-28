package com.pm.patientservice.controller;

import com.pm.patientservice.dto.BillResponseDTO;
import com.pm.patientservice.dto.PaymentResponseDTO;
import com.pm.patientservice.security.AuthorizationService;
import com.pm.patientservice.security.UserContext;
import com.pm.patientservice.service.BillService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patients")
@Tag(name = "Billing", description = "Patient Billing APIs")
public class BillController {

    private final BillService billService;
    private final AuthorizationService authorizationService;

    public BillController(
            BillService billService,
            AuthorizationService authorizationService) {

        this.billService = billService;
        this.authorizationService = authorizationService;
    }

    @GetMapping("/{patientId}/bills/{billId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PATIENT')")
    public ResponseEntity<BillResponseDTO> getBill(
            @PathVariable String patientId,
            @PathVariable String billId) {

        UserContext user = getCurrentUser();

        authorizationService.authorizePatientBillAccess(
                user,
                patientId);

        return ResponseEntity.ok(
                billService.getBill(patientId, billId));
    }

    @PutMapping("/{patientId}/bills/{billId}/pay")
    @PreAuthorize("hasAnyRole('ADMIN', 'PATIENT')")
    public ResponseEntity<PaymentResponseDTO> payBill(
            @PathVariable String patientId,
            @PathVariable String billId) {

        UserContext user = getCurrentUser();
        authorizationService.authorizePayBill(user, patientId);

        return ResponseEntity.ok(
                billService.payBill(patientId, billId));
    }

    private UserContext getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        return (UserContext) authentication.getPrincipal();
    }
}