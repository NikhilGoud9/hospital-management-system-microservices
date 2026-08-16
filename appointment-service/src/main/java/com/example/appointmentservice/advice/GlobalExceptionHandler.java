package com.example.appointmentservice.advice;

import com.example.appointmentservice.common.response.ApiErrorResponse;
import com.example.appointmentservice.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // ===============================
    // 🔴 NOT FOUND EXCEPTIONS
    // ===============================

    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handlePatientNotFound(
            PatientNotFoundException ex,
            HttpServletRequest request) {

        log.warn("Patient not found: {}", ex.getMessage());

        return buildErrorResponse(ex, request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DoctorNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleDoctorNotFound(
            DoctorNotFoundException ex,
            HttpServletRequest request) {

        log.warn("Doctor not found: {}", ex.getMessage());

        return buildErrorResponse(ex, request, HttpStatus.NOT_FOUND);
    }

    // ===============================
    // 🟠 BUSINESS VALIDATION
    // ===============================

    @ExceptionHandler(PatientInactiveException.class)
    public ResponseEntity<ApiErrorResponse> handlePatientInactive(
            PatientInactiveException ex,
            HttpServletRequest request) {

        log.warn("Inactive patient access attempt");

        return buildErrorResponse(ex, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DoctorInactiveException.class)
    public ResponseEntity<ApiErrorResponse> handleDoctorInactive(
            DoctorInactiveException ex,
            HttpServletRequest request) {

        log.warn("Inactive doctor access attempt");

        return buildErrorResponse(ex, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SlotAlreadyBookedException.class)
    public ResponseEntity<ApiErrorResponse> handleSlotConflict(
            SlotAlreadyBookedException ex,
            HttpServletRequest request) {

        log.warn("Slot already booked");

        return buildErrorResponse(ex, request, HttpStatus.CONFLICT);
    }

    // ===============================
    // 🟡 VALIDATION ERRORS
    // ===============================

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity.badRequest().body(errors);
    }

    // ===============================
    // 🔵 GENERIC EXCEPTION
    // ===============================

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGenericException(
            Exception ex,
            HttpServletRequest request) {

        log.error("Unexpected error occurred", ex);

        return buildErrorResponse(
                new RuntimeException("Something went wrong. Please contact administrator."),
                request,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    // ===============================
    // 🟢 COMMON BUILDER METHOD
    // ===============================

    private ResponseEntity<ApiErrorResponse> buildErrorResponse(
            Exception ex,
            HttpServletRequest request,
            HttpStatus status) {

        ApiErrorResponse error = ApiErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(status).body(error);
    }
}