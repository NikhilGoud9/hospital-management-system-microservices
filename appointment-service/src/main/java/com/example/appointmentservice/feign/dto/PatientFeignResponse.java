package com.example.appointmentservice.feign.dto;

import com.example.appointmentservice.feign.enums.PatientStatus;

public record PatientFeignResponse(
        Long patientId,
        String firstName,
        String lastName,
        PatientStatus status
) {}