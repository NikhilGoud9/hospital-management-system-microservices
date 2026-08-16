package com.example.appointmentservice.feign;

import com.example.appointmentservice.common.response.ApiResponse;
import com.example.appointmentservice.feign.dto.PatientFeignResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "patient-service")
public interface PatientClient {

    @GetMapping("/api/v1/patients/{id}")
    ApiResponse<PatientFeignResponse> getPatientById(@PathVariable Long id);
}