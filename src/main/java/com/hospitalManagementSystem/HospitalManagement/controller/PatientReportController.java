package com.hospitalManagementSystem.HospitalManagement.controller;

import com.hospitalManagementSystem.HospitalManagement.Entity.PatientReport;
import com.hospitalManagementSystem.HospitalManagement.service.PatientReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/api/reports")
public class PatientReportController {
    @Autowired
    private PatientReportService patientReportService;

    //Doctor adds the report
    @PostMapping("/add")
    public ResponseEntity<PatientReport> addReport(@RequestBody PatientReport report){
        return ResponseEntity.ok(patientReportService.saveReport(report));
    }

    //Patient fetches report using credentials
    @GetMapping("/fetch")
    public ResponseEntity<?> getReport(@RequestParam String patientName,
                                       @RequestParam String appointmentNumber,
                                       @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate appointmentDate,
                                       @RequestParam String email){
        Optional<PatientReport> report = patientReportService.getReport(
                patientName, appointmentNumber, appointmentDate, email
        );
        if (report.isPresent()){
            return ResponseEntity.ok(report.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
        }
    }


    }


