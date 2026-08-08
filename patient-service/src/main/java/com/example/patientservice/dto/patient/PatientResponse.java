package com.example.patientservice.dto.patient;

import com.example.patientservice.model.enums.Gender;
import com.example.patientservice.model.enums.PatientStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record PatientResponse(
        Long patientId,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        LocalDate dateOfBirth,
        Gender gender,
        String address,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        PatientStatus status

) {
}
