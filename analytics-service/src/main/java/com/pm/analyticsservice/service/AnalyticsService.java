package com.pm.analyticsservice.service;

import billing.events.BillingEvent;
import com.pm.analyticsservice.document.BillingProjection;
import com.pm.analyticsservice.document.DoctorProjection;
import com.pm.analyticsservice.document.PatientProjection;
import com.pm.analyticsservice.document.PrescriptionProjection;
import doctor.events.DoctorEvent;
import patient.events.PatientEvent;
import prescription.events.PrescriptionEvent;

import java.util.List;

public interface AnalyticsService {

    void handlePatientEvent(PatientEvent event);

    void handleDoctorEvent(DoctorEvent event);

    void handleBillingEvent(BillingEvent event);

    void handlePrescriptionEvent(PrescriptionEvent event);

    List<PatientProjection> getAllPatients();

    List<DoctorProjection> getAllDoctors();

    List<BillingProjection> getAllBillings();

    List<PrescriptionProjection> getAllPrescriptions();
}