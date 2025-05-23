package com.hospitalManagementSystem.HospitalManagement.repository;

import com.hospitalManagementSystem.HospitalManagement.Entity.PatientReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface PatientReportRepository extends JpaRepository<PatientReport, Long> {
    Optional<PatientReport> findByPatientNameAndAppointmentNumberAndAppointmentDateAndEmail(
            String patientName, String appointmentNumber, LocalDate appointmentDate, String email);


}
