package com.hospitalManagementSystem.HospitalManagement.service;

import com.hospitalManagementSystem.HospitalManagement.Entity.Doctor;
import com.hospitalManagementSystem.HospitalManagement.dto.DoctorRequest;
import com.hospitalManagementSystem.HospitalManagement.dto.DoctorResponse;
import com.hospitalManagementSystem.HospitalManagement.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorService {

    @Autowired
    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public DoctorResponse createDoctor(DoctorRequest request){
        Doctor doctor = Doctor.builder()
                .name(request.getName())
                .specialization(request.getSpecialization())
                .phone(request.getPhone())
                .email(request.getEmail())
                .experienceYear(request.getExperienceYears())
                .build();

        doctorRepository.save(doctor);

        return mapToResponse(doctor);
    }
    public List<DoctorResponse> getAllDoctor(){
        return doctorRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public DoctorResponse getDoctorById(Long id){
        Doctor doctor = doctorRepository.findById(id).orElseThrow(() -> new RuntimeException("Doctor not found"));
        return mapToResponse(doctor);
    }

    public DoctorResponse updateDoctor(Long id, DoctorRequest req) {
        Doctor doctor = doctorRepository.findById(id).orElseThrow(() -> new RuntimeException("Doctor not found"));
        doctor.setName(req.getName());
        doctor.setPhone(req.getPhone());
        doctor.setSpecialization(req.getSpecialization());
        doctor.setEmail(req.getEmail());
        doctor.setExperienceYear(req.getExperienceYears());
        doctorRepository.save(doctor);
        return mapToResponse(doctor);
    }

    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }

    private DoctorResponse mapToResponse(Doctor doctor) {
        return new DoctorResponse(
                doctor.getId(),
                doctor.getName(),
                doctor.getSpecialization(),
                doctor.getPhone(),
                doctor.getEmail(),
                doctor.getExperienceYear()
        );
    }

}
