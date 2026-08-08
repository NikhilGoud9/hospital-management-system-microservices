package com.example.patientservice.mapper;

import org.springframework.stereotype.Component;

import com.example.patientservice.dto.patient.PatientRequest;
import com.example.patientservice.dto.patient.PatientResponse;
import com.example.patientservice.model.Patient;

@Component
public class PatientMapper {
    public Patient toEntity(PatientRequest request) {
        // copy request fields into a new Patient
         Patient patient = new Patient();

    patient.setFirstName(request.firstName());
    patient.setLastName(request.lastName());
    patient.setEmail(request.email());
    patient.setPhoneNumber(request.phoneNumber());
    patient.setDateOfBirth(request.dateOfBirth());
    patient.setGender(request.gender());
    patient.setAddress(request.address());

    return patient;
    }

    public PatientResponse toResponse(Patient patient) {
        // create PatientResponse from entity
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

    public void updateEntity(PatientRequest request, Patient patient) {
        // copy editable fields from request to existing entity
        patient.setFirstName(request.firstName());
        patient.setLastName(request.lastName());
        patient.setEmail(request.email());
        patient.setPhoneNumber(request.phoneNumber());
        patient.setDateOfBirth(request.dateOfBirth());
        patient.setGender(request.gender());
        patient.setAddress(request.address());
    }
}
