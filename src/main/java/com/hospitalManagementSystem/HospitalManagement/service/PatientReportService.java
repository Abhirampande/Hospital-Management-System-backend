package com.hospitalManagementSystem.HospitalManagement.service;

import com.hospitalManagementSystem.HospitalManagement.Entity.PatientReport;
import com.hospitalManagementSystem.HospitalManagement.repository.PatientReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class PatientReportService {

    @Autowired
    private PatientReportRepository patientReportRepository;

    public PatientReport saveReport(PatientReport report){
        return patientReportRepository.save(report);
    }


    public Optional<PatientReport> getReport(String patientName, String appointmentNumber,
                                             LocalDate appointmentDate, String email){
        return patientReportRepository.findByPatientNameAndAppointmentNumberAndAppointmentDateAndEmail(
                patientName, appointmentNumber,appointmentDate, email);
    }
}
