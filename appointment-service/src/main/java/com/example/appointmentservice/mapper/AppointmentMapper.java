package com.example.appointmentservice.mapper;

import com.example.appointmentservice.dto.appointment.AppointmentRequest;
import com.example.appointmentservice.dto.appointment.AppointmentResponse;
import com.example.appointmentservice.entity.Appointment;
import org.springframework.stereotype.Component;

@Component
public class AppointmentMapper {

    public Appointment toEntity(AppointmentRequest request) {

        Appointment appointment = new Appointment();

        appointment.setPatientId(request.patientId());
        appointment.setDoctorId(request.doctorId());
        appointment.setAppointmentDate(request.appointmentDate());
        appointment.setAppointmentTime(request.appointmentTime());
        appointment.setReason(request.reason());

        return appointment;
    }

    public AppointmentResponse toResponse(Appointment appointment) {

        return new AppointmentResponse(
                appointment.getAppointmentId(),
                appointment.getPatientId(),
                appointment.getDoctorId(),
                appointment.getAppointmentDate(),
                appointment.getAppointmentTime(),
                appointment.getReason(),
                appointment.getStatus(),
                appointment.getCreatedAt(),
                appointment.getUpdatedAt()
        );
    }

    public void updateEntity(AppointmentRequest request, Appointment appointment) {

        appointment.setPatientId(request.patientId());
        appointment.setDoctorId(request.doctorId());
        appointment.setAppointmentDate(request.appointmentDate());
        appointment.setAppointmentTime(request.appointmentTime());
        appointment.setReason(request.reason());
    }
}