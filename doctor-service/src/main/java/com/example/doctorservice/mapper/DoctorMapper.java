package com.example.doctorservice.mapper;

import com.example.doctorservice.dto.doctor.DoctorRequest;
import com.example.doctorservice.dto.doctor.DoctorResponse;
import com.example.doctorservice.entity.Doctor;
import org.springframework.stereotype.Component;

@Component
public class DoctorMapper {
    public Doctor toEntity(DoctorRequest request) {
        
        Doctor doctor = new Doctor();

        doctor.setFirstName(request.firstName());
        doctor.setLastName(request.lastName());
        doctor.setEmail(request.email());
        doctor.setPhoneNumber(request.phoneNumber());
        doctor.setSpecialization(request.specialization());
        doctor.setQualification(request.qualification());
        doctor.setYearsOfExperience(request.yearsOfExperience());
        doctor.setConsultationFee(request.consultationFee());

        return doctor;
    }

    public DoctorResponse toResponse(Doctor doctor) {

        return new DoctorResponse(
                doctor.getDoctorId(),
                doctor.getFirstName(),
                doctor.getLastName(),
                doctor.getEmail(),
                doctor.getPhoneNumber(),
                doctor.getSpecialization(),
                doctor.getQualification(),
                doctor.getYearsOfExperience(),
                doctor.getConsultationFee(),
                doctor.getStatus()
        );
    }

    public void updateEntity(DoctorRequest request, Doctor doctor) {

        doctor.setFirstName(request.firstName());
        doctor.setLastName(request.lastName());
        doctor.setEmail(request.email());
        doctor.setPhoneNumber(request.phoneNumber());
        doctor.setSpecialization(request.specialization());
        doctor.setQualification(request.qualification());
        doctor.setYearsOfExperience(request.yearsOfExperience());
        doctor.setConsultationFee(request.consultationFee());

    }
}
