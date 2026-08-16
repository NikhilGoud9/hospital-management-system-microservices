package com.example.appointmentservice.repository;

import com.example.appointmentservice.entity.Appointment;
import com.example.appointmentservice.entity.enums.AppointmentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // 🔥 Prevent double booking (core logic)
    boolean existsByDoctorIdAndAppointmentDateAndAppointmentTimeAndStatus(
            Long doctorId,
            LocalDate appointmentDate,
            LocalTime appointmentTime,
            AppointmentStatus status
    );

    // Get appointments for a patient
    Page<Appointment> findByPatientId(Long patientId, Pageable pageable);

    // Get appointments for a doctor
    Page<Appointment> findByDoctorId(Long doctorId, Pageable pageable);

    // 🔍 Search (basic)
    Page<Appointment> findByReasonContainingIgnoreCase(
            String keyword,
            Pageable pageable
    );
}