package com.example.appointmentservice.feign.dto;

import com.example.appointmentservice.feign.enums.DoctorStatus;

public record DoctorFeignResponse(
        Long doctorId,
        DoctorStatus status
) {}