package com.example.doctorservice.service.impl;

import com.example.doctorservice.dto.doctor.DoctorRequest;
import com.example.doctorservice.dto.doctor.DoctorResponse;
import com.example.doctorservice.entity.Doctor;
import com.example.doctorservice.entity.enums.DoctorStatus;
import com.example.doctorservice.exception.DoctorNotFoundException;
import com.example.doctorservice.exception.DuplicateDoctorException;
import com.example.doctorservice.mapper.DoctorMapper;
import com.example.doctorservice.repository.DoctorRepository;
import com.example.doctorservice.service.DoctorService;
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
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;

    private Doctor getDoctor(Long doctorId) {
        return doctorRepository.findById(doctorId)
                .orElseThrow(() -> new DoctorNotFoundException(doctorId));
    }

    @Transactional
    @Override
    public DoctorResponse saveDoctor(DoctorRequest request) {

        log.info("Creating doctor with email {}", request.email());

        if (doctorRepository.existsByEmail(request.email())) {
            throw new DuplicateDoctorException(
                    "Doctor already exists with email: " + request.email());
        }

        if (doctorRepository.existsByPhoneNumber(request.phoneNumber())) {
            throw new DuplicateDoctorException(
                    "Doctor already exists with phone number: " + request.phoneNumber());
        }

        Doctor doctor = doctorMapper.toEntity(request);

        Doctor savedDoctor = doctorRepository.save(doctor);

        log.info("Doctor created successfully with id {}", savedDoctor.getDoctorId());

        return doctorMapper.toResponse(savedDoctor);
    }

    @Override
    public DoctorResponse getDoctorById(Long doctorId) {

        log.info("Fetching doctor with id {}", doctorId);

        return doctorMapper.toResponse(getDoctor(doctorId));
    }

    @Override
    public Page<DoctorResponse> getAllDoctors(Pageable pageable) {

        log.info("Fetching doctors page {}", pageable.getPageNumber());

        return doctorRepository.findAll(pageable)
                .map(doctorMapper::toResponse);
    }

    @Override
    public Page<DoctorResponse> searchDoctors(String keyword,
                                              Pageable pageable) {

        log.info("Searching doctors with keyword {}", keyword);

        return doctorRepository.searchDoctors(keyword, pageable)
                .map(doctorMapper::toResponse);
    }

    @Transactional
    @Override
    public DoctorResponse updateDoctor(Long doctorId,
                                       DoctorRequest request) {

        log.info("Updating doctor {}", doctorId);

        Doctor doctor = getDoctor(doctorId);

        if (!doctor.getEmail().equals(request.email())
                && doctorRepository.existsByEmail(request.email())) {

            throw new DuplicateDoctorException(
                    "Doctor already exists with email: " + request.email());
        }

        if (!doctor.getPhoneNumber().equals(request.phoneNumber())
                && doctorRepository.existsByPhoneNumber(request.phoneNumber())) {

            throw new DuplicateDoctorException(
                    "Doctor already exists with phone number: " + request.phoneNumber());
        }

        doctorMapper.updateEntity(request, doctor);

        Doctor updatedDoctor = doctorRepository.save(doctor);

        log.info("Doctor updated successfully with id {}", doctorId);

        return doctorMapper.toResponse(updatedDoctor);
    }

    @Transactional
    @Override
    public void deactivateDoctor(Long doctorId) {

        log.info("Deactivating doctor {}", doctorId);

        Doctor doctor = getDoctor(doctorId);

        doctor.setStatus(DoctorStatus.INACTIVE);

        doctorRepository.save(doctor);

        log.info("Doctor deactivated successfully {}", doctorId);
    }
}