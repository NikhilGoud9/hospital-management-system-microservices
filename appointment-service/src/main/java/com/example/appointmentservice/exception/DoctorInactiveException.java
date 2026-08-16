package com.example.appointmentservice.exception;

public class DoctorInactiveException extends RuntimeException {

    public DoctorInactiveException() {
        super("Doctor is not active");
    }
}