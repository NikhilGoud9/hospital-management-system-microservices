package com.example.doctorservice.service;

import com.example.doctorservice.dto.doctor.DoctorRequest;
import com.example.doctorservice.dto.doctor.DoctorResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DoctorService {
    DoctorResponse saveDoctor(DoctorRequest request);

    DoctorResponse getDoctorById(Long doctorId);

    Page<DoctorResponse> getAllDoctors(Pageable pageable);

    DoctorResponse updateDoctor(Long doctorId, DoctorRequest request);

    void deactivateDoctor(Long doctorId);

    Page<DoctorResponse> searchDoctors(String keyword, Pageable pageable);
}
