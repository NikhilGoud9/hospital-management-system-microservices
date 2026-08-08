package com.example.doctorservice.repository;

import com.example.doctorservice.entity.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor,Long> {
    Optional<Doctor> findByEmail(String email);

    Optional<Doctor> findByPhoneNumber(String phoneNumber);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    @Query("""
SELECT d FROM Doctor d
WHERE LOWER(d.firstName) LIKE LOWER(CONCAT('%', :keyword, '%'))
OR LOWER(d.lastName) LIKE LOWER(CONCAT('%', :keyword, '%'))
OR LOWER(d.email) LIKE LOWER(CONCAT('%', :keyword, '%'))
OR LOWER(d.phoneNumber) LIKE LOWER(CONCAT('%', :keyword, '%'))
OR LOWER(d.specialization) LIKE LOWER(CONCAT('%', :keyword, '%'))
""")
    Page<Doctor> searchDoctors(String keyword, Pageable pageable);
}
