package com.example.appointmentservice.feign;

import com.example.appointmentservice.common.response.ApiResponse;
import com.example.appointmentservice.feign.dto.DoctorFeignResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "doctor-service")
public interface DoctorClient {

    @GetMapping("/api/v1/doctors/{id}")
    ApiResponse<DoctorFeignResponse> getDoctorById(@PathVariable Long id);
}