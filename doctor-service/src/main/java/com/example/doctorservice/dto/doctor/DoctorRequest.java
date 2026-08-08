package com.example.doctorservice.dto.doctor;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DoctorRequest(
        @NotBlank(message = "First name is required")
        @Size(min = 2,max = 50)
        String firstName,

        @NotBlank(message = "Last name is required")
        @Size(min = 2,max = 50)
        String lastName,

        @NotBlank
        @Size(max = 100)
        @Email(message = "Email should be valid")
        String email,

        @NotBlank(message = "Phone number is required")
        @Pattern(
                regexp = "^[0-9]{10}$",
                message = "Phone number must contain exactly 10 digits"
        )
        String phoneNumber,

        @NotBlank(message = "specialization is required")
        String specialization,

        @NotBlank(message = "Qualification is required")
        String qualification,

        @NotNull(message = "Years of experience is required")
        @Min(value = 0, message = "Experience cannot be negative")
        Integer yearsOfExperience,

        @NotNull(message = "consultaion fee is requird")
        @Positive(message = "Consultation fee must be greater than 0")
        BigDecimal consultationFee
) {
}
