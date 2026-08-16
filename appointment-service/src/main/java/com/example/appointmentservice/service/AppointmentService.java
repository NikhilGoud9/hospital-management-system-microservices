package com.example.appointmentservice.service;

import com.example.appointmentservice.dto.appointment.AppointmentRequest;
import com.example.appointmentservice.dto.appointment.AppointmentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AppointmentService {

    AppointmentResponse bookAppointment(AppointmentRequest request);

    AppointmentResponse getAppointmentById(Long id);

    Page<AppointmentResponse> getAllAppointments(Pageable pageable);

    AppointmentResponse updateAppointment(Long id, AppointmentRequest request);

    void cancelAppointment(Long id);

    Page<AppointmentResponse> searchAppointments(String keyword, Pageable pageable);

    Page<AppointmentResponse> getAppointmentsByPatient(Long patientId, Pageable pageable);

    Page<AppointmentResponse> getAppointmentsByDoctor(Long doctorId, Pageable pageable);
}