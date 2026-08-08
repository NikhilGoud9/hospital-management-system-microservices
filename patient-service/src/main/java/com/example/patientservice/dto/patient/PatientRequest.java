package com.example.patientservice.dto.patient;

import com.example.patientservice.model.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record PatientRequest(
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
        String phoneNumber,

        @Past
        @NotNull(message = "Date of birth is required") 
        LocalDate dateOfBirth,

        @NotNull(message = "Gender is required") 
        Gender gender,

        @Size(max = 255)
        String address
) {}
