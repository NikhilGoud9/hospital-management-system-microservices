package com.example.doctorservice.controller;

import com.example.doctorservice.common.constants.MessageConstants;
import com.example.doctorservice.common.response.ApiResponse;
import com.example.doctorservice.common.response.ApiResponseUtil;
import com.example.doctorservice.dto.doctor.DoctorRequest;
import com.example.doctorservice.dto.doctor.DoctorResponse;
import com.example.doctorservice.service.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/doctors")
@RequiredArgsConstructor
@Slf4j
public class DoctorController {

    private final DoctorService doctorService;

    @PostMapping
    public ResponseEntity<ApiResponse<DoctorResponse>> createDoctor(
            @Valid @RequestBody DoctorRequest request) {

        log.info("Received request to create doctor");

        DoctorResponse response = doctorService.saveDoctor(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseUtil.created(
                        MessageConstants.DOCTOR_CREATED_SUCCESS,
                        response));
    }

    @GetMapping("/{doctorId}")
    public ResponseEntity<ApiResponse<DoctorResponse>> getDoctorById(
            @PathVariable Long doctorId) {

        log.info("Received request to fetch doctor with id : {}", doctorId);

        DoctorResponse response = doctorService.getDoctorById(doctorId);

        return ResponseEntity.ok(
                ApiResponseUtil.success(
                        MessageConstants.DOCTOR_FETCHED_SUCCESS,
                        response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<DoctorResponse>>> getAllDoctors(
            Pageable pageable) {

        log.info("Received request to fetch all doctors");

        Page<DoctorResponse> response = doctorService.getAllDoctors(pageable);

        return ResponseEntity.ok(
                ApiResponseUtil.success(
                        MessageConstants.DOCTORS_FETCHED_SUCCESS,
                        response));
    }

    @PutMapping("/{doctorId}")
    public ResponseEntity<ApiResponse<DoctorResponse>> updateDoctor(
            @PathVariable Long doctorId,
            @Valid @RequestBody DoctorRequest request) {

        log.info("Received request to update doctor with id : {}", doctorId);

        DoctorResponse response = doctorService.updateDoctor(doctorId, request);

        return ResponseEntity.ok(
                ApiResponseUtil.success(
                        MessageConstants.DOCTOR_UPDATED_SUCCESS,
                        response));
    }

    @PatchMapping("/{doctorId}/status")
    public ResponseEntity<ApiResponse<Void>> deactivateDoctor(
            @PathVariable Long doctorId) {

        log.info("Received request to deactivate doctor with id : {}", doctorId);

        doctorService.deactivateDoctor(doctorId);

        return ResponseEntity.ok(
                ApiResponseUtil.success(
                        MessageConstants.DOCTOR_DEACTIVATED_SUCCESS,
                        null));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<DoctorResponse>>> searchDoctors(
            @RequestParam String keyword,
            Pageable pageable) {

        log.info("Searching doctors with keyword : {}", keyword);

        Page<DoctorResponse> response =
                doctorService.searchDoctors(keyword, pageable);

        return ResponseEntity.ok(
                ApiResponseUtil.success(
                        MessageConstants.DOCTORS_FETCHED_SUCCESS,
                        response));
    }

}