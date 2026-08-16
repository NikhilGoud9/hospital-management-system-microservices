package com.example.appointmentservice.dto.appointment;

import com.example.appointmentservice.entity.enums.AppointmentStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record AppointmentResponse(

        Long appointmentId,
        Long patientId,
        Long doctorId,
        LocalDate appointmentDate,
        LocalTime appointmentTime,
        String reason,
        AppointmentStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}