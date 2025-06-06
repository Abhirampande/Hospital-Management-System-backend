package com.hospitalManagementSystem.HospitalManagement.controller;

import com.hospitalManagementSystem.HospitalManagement.dto.JwtResponse;
import com.hospitalManagementSystem.HospitalManagement.dto.LoginRequest;
import com.hospitalManagementSystem.HospitalManagement.dto.RegisterRequest;
import com.hospitalManagementSystem.HospitalManagement.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    public final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<JwtResponse> register(@RequestBody RegisterRequest request){
        String token = authenticationService.register(request);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest request){
        String token = authenticationService.login(request);
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
