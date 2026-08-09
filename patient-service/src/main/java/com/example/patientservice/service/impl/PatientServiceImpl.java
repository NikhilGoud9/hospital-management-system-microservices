package com.example.patientservice.service.impl;

import com.example.patientservice.dto.patient.PatientRequest;
import com.example.patientservice.dto.patient.PatientResponse;
import com.example.patientservice.exception.DuplicatePatientException;
import com.example.patientservice.exception.PatientNotFoundException;
import com.example.patientservice.mapper.PatientMapper;
import com.example.patientservice.model.Patient;
import com.example.patientservice.model.enums.PatientStatus;
import com.example.patientservice.repository.PatientRepository;
import com.example.patientservice.service.PatientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    @Transactional
    @Override
    public PatientResponse savePatient(PatientRequest request) {

        log.info("Creating patient with email {}", request.email());

        if (patientRepository.existsByEmail(request.email())) {
            throw new DuplicatePatientException(
                    "Patient already exists with email: " + request.email());
        }

        if (patientRepository.existsByPhoneNumber(request.phoneNumber())) {
            throw new DuplicatePatientException(
                    "Patient already exists with phone number: " + request.phoneNumber());
        }

        Patient patient = patientMapper.toEntity(request);

        Patient savedPatient = patientRepository.save(patient);

        log.info("Patient created successfully with id {}", savedPatient.getPatientId());

        return patientMapper.toResponse(savedPatient);
    }

    @Override
    public PatientResponse getPatientById(Long id) {

        log.info("Fetching patient with id {}", id);

        return patientMapper.toResponse(getPatient(id));
    }

    @Override
    public Page<PatientResponse> getAllPatients(Pageable pageable) {

        log.info("Fetching patients page {}", pageable.getPageNumber());

        return patientRepository.findAll(pageable)
                .map(patientMapper::toResponse);
    }

    @Transactional
    @Override
    public PatientResponse updatePatient(Long id, PatientRequest request) {

        log.info("Updating patient {}", id);

        Patient patient = getPatient(id);

        if (!patient.getEmail().equals(request.email())
                && patientRepository.existsByEmail(request.email())) {

            throw new DuplicatePatientException(
                    "Patient already exists with email: " + request.email());
        }

        if (!patient.getPhoneNumber().equals(request.phoneNumber())
                && patientRepository.existsByPhoneNumber(request.phoneNumber())) {

            throw new DuplicatePatientException(
                    "Patient already exists with phone number: " + request.phoneNumber());
        }

        patientMapper.updateEntity(request, patient);

        Patient updatedPatient = patientRepository.save(patient);

        log.info("Patient updated successfully with id {}", id);

        return patientMapper.toResponse(updatedPatient);
    }

    @Transactional
    @Override
    public void deactivatePatient(Long id) {

        log.info("Deactivating patient {}", id);

        Patient patient = getPatient(id);

        patient.setStatus(PatientStatus.INACTIVE);

        patientRepository.save(patient);

        log.info("Patient deactivated successfully {}", id);
    }

    @Override
    public Page<PatientResponse> searchPatients(String keyword,
                                                Pageable pageable) {

        log.info("Searching patients with keyword {}", keyword);

        return patientRepository.searchPatients(keyword, pageable)
                .map(patientMapper::toResponse);
    }

    private Patient getPatient(Long id) {

        return patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException(id));
    }
}