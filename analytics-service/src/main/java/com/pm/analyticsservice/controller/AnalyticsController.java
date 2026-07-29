package com.pm.analyticsservice.controller;

import com.pm.analyticsservice.document.BillingProjection;
import com.pm.analyticsservice.document.DoctorProjection;
import com.pm.analyticsservice.document.PatientProjection;
import com.pm.analyticsservice.document.PrescriptionProjection;
import com.pm.analyticsservice.service.AnalyticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/analytics")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping("/patients")
    public ResponseEntity<List<PatientProjection>> getAllPatients() {
        return ResponseEntity.ok(
                analyticsService.getAllPatients());
    }

    @GetMapping("/doctors")
    public ResponseEntity<List<DoctorProjection>> getAllDoctors() {
        return ResponseEntity.ok(
                analyticsService.getAllDoctors());
    }

    @GetMapping("/prescriptions")
    public ResponseEntity<List<PrescriptionProjection>> getAllPrescriptions() {
        return ResponseEntity.ok(
                analyticsService.getAllPrescriptions());
    }

    @GetMapping("/billings")
    public ResponseEntity<List<BillingProjection>> getAllBillings() {
        return ResponseEntity.ok(
                analyticsService.getAllBillings());
    }
}