package com.pm.doctorservice.security;

public final class SecurityMessages {

    public static final String ACCESS_DENIED =
            "Access denied.";

    public static final String DOCTOR_OWN_PRESCRIPTION =
            "Doctors can only create prescriptions for themselves.";

    public static final String DOCTOR_OWN_HISTORY =
            "Doctors can only view their own prescription history.";

    public static final String PATIENT_OWN_HISTORY =
            "Patients can only view their own prescription history.";

    public static final String DOCTOR_DELETE_OWN =
            "Doctors can only delete their own prescriptions.";

    private SecurityMessages() {}
}