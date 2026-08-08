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

@Service
@RequiredArgsConstructor
@Slf4j
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;

    @Override
    public DoctorResponse saveDoctor(DoctorRequest request) {

        log.info("Creating doctor with email : {}", request.email());

        if (doctorRepository.existsByEmail(request.email())) {
            throw new DuplicateDoctorException("Doctor already exists with email : " + request.email());
        }

        if (doctorRepository.existsByPhoneNumber(request.phoneNumber())) {
            throw new DuplicateDoctorException("Doctor already exists with phone number : " + request.phoneNumber());
        }

        Doctor doctor = doctorMapper.toEntity(request);

        Doctor savedDoctor = doctorRepository.save(doctor);

        log.info("Doctor created successfully with id : {}", savedDoctor.getDoctorId());

        return doctorMapper.toResponse(savedDoctor);
    }

    @Override
    public DoctorResponse getDoctorById(Long doctorId) {

        log.info("Fetching doctor with id : {}", doctorId);

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() ->
                        new DoctorNotFoundException(doctorId));

        return doctorMapper.toResponse(doctor);
    }

    @Override
    public Page<DoctorResponse> getAllDoctors(Pageable pageable) {

        log.info("Fetching all doctors");

        return doctorRepository.findAll(pageable)
                .map(doctorMapper::toResponse);
    }

    @Override
    public DoctorResponse updateDoctor(Long doctorId, DoctorRequest request) {

        log.info("Updating doctor with id : {}", doctorId);

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() ->
                        new DoctorNotFoundException(doctorId));

        if (!doctor.getEmail().equals(request.email())
                && doctorRepository.existsByEmail(request.email())) {

            throw new DuplicateDoctorException("Email already exists : " + request.email());
        }

        if (!doctor.getPhoneNumber().equals(request.phoneNumber())
                && doctorRepository.existsByPhoneNumber(request.phoneNumber())) {

            throw new DuplicateDoctorException("Phone number already exists : " + request.phoneNumber());
        }

        doctorMapper.updateEntity(request, doctor);

        Doctor updatedDoctor = doctorRepository.save(doctor);

        log.info("Doctor updated successfully with id : {}", updatedDoctor.getDoctorId());

        return doctorMapper.toResponse(updatedDoctor);
    }

    @Override
    public void deactivateDoctor(Long doctorId) {

        log.info("Deactivating doctor with id : {}", doctorId);

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() ->
                        new DoctorNotFoundException(doctorId));

        doctor.setStatus(DoctorStatus.INACTIVE);

        doctorRepository.save(doctor);

        log.info("Doctor deactivated successfully with id : {}", doctorId);
    }

    @Override
    public Page<DoctorResponse> searchDoctors(String keyword, Pageable pageable) {

        log.info("Searching doctors with keyword : {}", keyword);

        return doctorRepository.searchDoctors(keyword, pageable)
                .map(doctorMapper::toResponse);
    }
}