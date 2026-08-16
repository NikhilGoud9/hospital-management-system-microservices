package com.example.appointmentservice.service.impl;

import com.example.appointmentservice.dto.appointment.AppointmentRequest;
import com.example.appointmentservice.dto.appointment.AppointmentResponse;
import com.example.appointmentservice.entity.Appointment;
import com.example.appointmentservice.entity.enums.AppointmentStatus;
import com.example.appointmentservice.exception.*;
import com.example.appointmentservice.feign.DoctorClient;
import com.example.appointmentservice.feign.PatientClient;
import com.example.appointmentservice.feign.dto.DoctorFeignResponse;
import com.example.appointmentservice.feign.dto.PatientFeignResponse;
import com.example.appointmentservice.feign.enums.DoctorStatus;
import com.example.appointmentservice.feign.enums.PatientStatus;
import com.example.appointmentservice.mapper.AppointmentMapper;
import com.example.appointmentservice.repository.AppointmentRepository;
import com.example.appointmentservice.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository repository;
    private final AppointmentMapper mapper;
    private final PatientClient patientClient;
    private final DoctorClient doctorClient;

    //  Helper
    private Appointment getAppointment(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found: " + id));
    }

    @Transactional
    @Override
    public AppointmentResponse bookAppointment(AppointmentRequest request) {
        log.info(request.toString());
        log.info("Booking appointment for doctor {} at {} {}",
                request.doctorId(),
                request.appointmentDate(),
                request.appointmentTime());

        // ✅ Validate Patient
        PatientFeignResponse patient;
        log.info(String.valueOf(request.patientId()));
        try {
            patient = patientClient.getPatientById(request.patientId()).getData();
            log.info("i m here");
            log.info(patient.toString());
        } catch (Exception ex) {
            throw new PatientNotFoundException(request.patientId());
        }
        log.info("Patient status: {}", patient.status());
        if (patient.status() != PatientStatus.ACTIVE) {
            log.info("Patient status: {}", patient.status());
            throw new PatientInactiveException();
        }


// ✅ Validate Doctor
        DoctorFeignResponse doctor;

        try {
            doctor = doctorClient.getDoctorById(request.doctorId()).getData();
        } catch (Exception ex) {
            throw new DoctorNotFoundException(request.doctorId());
        }

        if (doctor.status() != DoctorStatus.ACTIVE) {
            throw new DoctorInactiveException();
        }

        //  Slot validation
        boolean exists = repository
                .existsByDoctorIdAndAppointmentDateAndAppointmentTimeAndStatus(
                        request.doctorId(),
                        request.appointmentDate(),
                        request.appointmentTime(),
                        AppointmentStatus.SCHEDULED
                );

        if (exists) {
            throw new SlotAlreadyBookedException(
                    "Doctor already has an appointment at this time");
        }

        Appointment appointment = mapper.toEntity(request);

        Appointment saved = repository.save(appointment);

        log.info("Appointment booked with id {}", saved.getAppointmentId());

        return mapper.toResponse(saved);
    }

    @Override
    public AppointmentResponse getAppointmentById(Long id) {

        log.info("Fetching appointment {}", id);

        return mapper.toResponse(getAppointment(id));
    }

    @Override
    public Page<AppointmentResponse> getAllAppointments(Pageable pageable) {

        log.info("Fetching all appointments");

        return repository.findAll(pageable)
                .map(mapper::toResponse);
    }

    @Transactional
    @Override
    public AppointmentResponse updateAppointment(Long id, AppointmentRequest request) {

        log.info("Updating appointment {}", id);

        Appointment appointment = getAppointment(id);

        //  Re-check slot if changed
        boolean exists = repository
                .existsByDoctorIdAndAppointmentDateAndAppointmentTimeAndStatus(
                        request.doctorId(),
                        request.appointmentDate(),
                        request.appointmentTime(),
                        AppointmentStatus.SCHEDULED
                );

        if (exists) {
            throw new SlotAlreadyBookedException(
                    "Doctor already has an appointment at this time");
        }

        mapper.updateEntity(request, appointment);

        Appointment updated = repository.save(appointment);

        return mapper.toResponse(updated);
    }

    @Transactional
    @Override
    public void cancelAppointment(Long id) {

        log.info("Cancelling appointment {}", id);

        Appointment appointment = getAppointment(id);

        appointment.setStatus(AppointmentStatus.CANCELLED);

        repository.save(appointment);
    }

    @Override
    public Page<AppointmentResponse> searchAppointments(String keyword, Pageable pageable) {

        return repository.findByReasonContainingIgnoreCase(keyword, pageable)
                .map(mapper::toResponse);
    }

    @Override
    public Page<AppointmentResponse> getAppointmentsByPatient(Long patientId, Pageable pageable) {

        return repository.findByPatientId(patientId, pageable)
                .map(mapper::toResponse);
    }

    @Override
    public Page<AppointmentResponse> getAppointmentsByDoctor(Long doctorId, Pageable pageable) {

        return repository.findByDoctorId(doctorId, pageable)
                .map(mapper::toResponse);
    }
}