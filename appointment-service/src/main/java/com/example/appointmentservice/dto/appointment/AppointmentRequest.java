package com.example.appointmentservice.dto.appointment;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.LocalTime;

public record AppointmentRequest(

        @NotNull(message = "Patient ID is required")
        Long patientId,

        @NotNull(message = "Doctor ID is required")
        Long doctorId,

        @NotNull(message = "Appointment date is required")
        LocalDate appointmentDate,

        @NotNull(message = "Appointment time is required")
        LocalTime appointmentTime,

        @Size(max = 255, message = "Reason must not exceed 255 characters")
        String reason
) {}