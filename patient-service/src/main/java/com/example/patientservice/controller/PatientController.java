package com.example.patientservice.controller;

import com.example.patientservice.common.response.ApiResponse;
import com.example.patientservice.common.response.ApiResponseUtil;
import com.example.patientservice.dto.patient.PatientRequest;
import com.example.patientservice.dto.patient.PatientResponse;
import com.example.patientservice.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @PostMapping
    public ResponseEntity<ApiResponse<PatientResponse>> createPatient(
            @Valid @RequestBody PatientRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseUtil.success(
                        "Patient created successfully",
                        patientService.savePatient(request)
                ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PatientResponse>> getPatient(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponseUtil.success(
                        "Patient fetched successfully",
                        patientService.getPatientById(id)
                )
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<PatientResponse>>> getAllPatients(
            Pageable pageable) {

        return ResponseEntity.ok(
                ApiResponseUtil.success(
                        "Patients fetched successfully",
                        patientService.getAllPatients(pageable)
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PatientResponse>> updatePatient(
            @PathVariable Long id,
            @Valid @RequestBody PatientRequest request) {

        return ResponseEntity.ok(
                ApiResponseUtil.success(
                        "Patient updated successfully",
                        patientService.updatePatient(id, request)
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(
            @PathVariable Long id) {

        patientService.deletePatient(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<PatientResponse>>> searchPatients(
            @RequestParam String keyword,
            Pageable pageable) {

        return ResponseEntity.ok(
                ApiResponseUtil.success(
                        "Patients fetched successfully",
                        patientService.searchPatients(keyword, pageable)
                )
        );
    }
}