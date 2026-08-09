package com.example.patientservice.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.patientservice.dto.patient.PatientRequest;
import com.example.patientservice.dto.patient.PatientResponse;



public interface PatientService {

    PatientResponse savePatient(PatientRequest request);

    PatientResponse getPatientById(Long id);

    Page<PatientResponse> getAllPatients(Pageable pageable);

    PatientResponse updatePatient(Long id, PatientRequest request);

    void deactivatePatient(Long id);

    Page<PatientResponse> searchPatients(String keyword, Pageable pageable);

}