package com.example.patientservice.dto.patient;

import com.example.patientservice.model.Patient;
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
    public static PatientResponse fromEntity(Patient patient) {
        return new PatientResponse(
                patient.getPatientId(),
                patient.getFirstName(),
                patient.getLastName(),
                patient.getEmail(),
                patient.getPhoneNumber(),
                patient.getDateOfBirth(),
                patient.getGender(),
                patient.getAddress(),
                patient.getCreatedAt(),
                patient.getUpdatedAt(),
                patient.getStatus()

        );
    }
}
