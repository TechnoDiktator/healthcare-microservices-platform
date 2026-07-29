package com.pm.patientservice.controller;

import com.pm.patientservice.dto.BillResponseDTO;
import com.pm.patientservice.dto.PaymentResponseDTO;
import com.pm.patientservice.security.AuthorizationService;
import com.pm.patientservice.security.UserContext;
import com.pm.patientservice.service.BillService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patients")
@Tag(name = "Billing", description = "Patient Billing APIs")
public class BillController {

    private static final Logger log =
            LoggerFactory.getLogger(BillController.class);

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

        log.info(
                "Get Bill request received. userId={}, email={}, role={}, patientId={}, billId={}",
                user.getUserId(),
                user.getEmail(),
                user.getRole(),
                patientId,
                billId
        );

        authorizationService.authorizePatientBillAccess(user, patientId);

        log.info(
                "Authorization successful for user {} to access bill {}",
                user.getEmail(),
                billId
        );

        BillResponseDTO response = billService.getBill(patientId, billId);

        log.info(
                "Successfully fetched bill {} for patient {}",
                billId,
                patientId
        );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{patientId}/bills/{billId}/pay")
    @PreAuthorize("hasAnyRole('ADMIN', 'PATIENT')")
    public ResponseEntity<PaymentResponseDTO> payBill(
            @PathVariable String patientId,
            @PathVariable String billId) {

        UserContext user = getCurrentUser();

        log.info(
                "Pay Bill request received. userId={}, email={}, role={}, patientId={}, billId={}",
                user.getUserId(),
                user.getEmail(),
                user.getRole(),
                patientId,
                billId
        );

        authorizationService.authorizePayBill(user, patientId);

        log.info(
                "Authorization successful for payment. user={}, billId={}",
                user.getEmail(),
                billId
        );

        PaymentResponseDTO response = billService.payBill(patientId, billId);

        log.info(
                "Successfully paid bill {} for patient {}",
                billId,
                patientId
        );

        return ResponseEntity.ok(response);
    }

    private UserContext getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        log.info("Authentication: {}", authentication);
        log.info("Authorities: {}", authentication.getAuthorities());

        return (UserContext) authentication.getPrincipal();
    }
}