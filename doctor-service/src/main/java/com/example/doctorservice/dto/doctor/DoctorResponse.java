package com.example.doctorservice.dto.doctor;

import com.example.doctorservice.entity.enums.DoctorStatus;


import java.math.BigDecimal;

public record DoctorResponse(
        Long doctorId,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        String specialization,
        String qualification,
        Integer yearsOfExperience,
        BigDecimal consultationFee,
        DoctorStatus status
) {
}
