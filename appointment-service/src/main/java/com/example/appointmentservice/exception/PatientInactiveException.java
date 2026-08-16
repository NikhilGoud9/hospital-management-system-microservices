package com.example.appointmentservice.exception;

public class PatientInactiveException extends RuntimeException {

    public PatientInactiveException() {
        super("Patient is not active");
    }
}