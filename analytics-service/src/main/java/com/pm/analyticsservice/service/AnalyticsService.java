package com.pm.analyticsservice.service;

import billing.events.BillingEvent;
import doctor.events.DoctorEvent;
import patient.events.PatientEvent;
import prescription.events.PrescriptionEvent;

public interface AnalyticsService {

    void handlePatientEvent(PatientEvent event);

    void handleDoctorEvent(DoctorEvent event);

    void handleBillingEvent(BillingEvent event);

    void handlePrescriptionEvent(PrescriptionEvent event);
}