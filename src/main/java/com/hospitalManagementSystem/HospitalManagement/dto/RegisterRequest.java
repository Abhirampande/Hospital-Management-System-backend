package com.hospitalManagementSystem.HospitalManagement.dto;

import lombok.Data;

import java.util.Set;

@Data
public class RegisterRequest {

    private String username;
    private String password;
    private String fullName;
    private String email;
    private Set<String> roles;


}
