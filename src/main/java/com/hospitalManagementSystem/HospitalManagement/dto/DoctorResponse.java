package com.hospitalManagementSystem.HospitalManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorResponse {

    private Long id;
    private String name;
    private String specialization;
    private String phone;
    private String email;
    private int experienceYears;
}
