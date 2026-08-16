package com.example.appointmentservice.controller;

import com.example.appointmentservice.common.response.ApiResponse;
import com.example.appointmentservice.common.response.ApiResponseUtil;
import com.example.appointmentservice.dto.appointment.AppointmentRequest;
import com.example.appointmentservice.dto.appointment.AppointmentResponse;
import com.example.appointmentservice.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/appointments")
@RequiredArgsConstructor
@Slf4j
public class AppointmentController {

    private final AppointmentService appointmentService;

    // ===============================
    // CREATE APPOINTMENT
    // ===============================
    @PostMapping
    public ApiResponse<AppointmentResponse> bookAppointment(
            @Valid @RequestBody AppointmentRequest request) {
    log.info(request.toString());
        AppointmentResponse response =
                appointmentService.bookAppointment(request);

        return ApiResponseUtil.success("Appointment created successfully", response);
    }

    // ===============================
    // GET BY ID
    // ===============================
    @GetMapping("/{id}")
    public ApiResponse<AppointmentResponse> getAppointmentById(
            @PathVariable Long id) {

        AppointmentResponse response =
                appointmentService.getAppointmentById(id);

        return ApiResponseUtil.success(response);
    }

    // ===============================
    // GET ALL (PAGINATION)
    // ===============================
    @GetMapping
    public ApiResponse getAllAppointments(
            Pageable pageable) {

        Page<AppointmentResponse> response =
                appointmentService.getAllAppointments(pageable);

        return ApiResponseUtil.success(response);
    }

    // ===============================
    // CANCEL APPOINTMENT
    // ===============================
    @DeleteMapping("/{id}")
    public ApiResponse<String> cancelAppointment(
            @PathVariable Long id) {

        appointmentService.cancelAppointment(id);

        return ApiResponseUtil.success("Appointment cancelled successfully");
    }
}