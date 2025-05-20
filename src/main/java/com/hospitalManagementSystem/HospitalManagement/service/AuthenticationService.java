package com.hospitalManagementSystem.HospitalManagement.service;


import com.hospitalManagementSystem.HospitalManagement.Entity.UserRole;
import com.hospitalManagementSystem.HospitalManagement.Entity.User;
import com.hospitalManagementSystem.HospitalManagement.dto.LoginRequest;
import com.hospitalManagementSystem.HospitalManagement.dto.RegisterRequest;
import com.hospitalManagementSystem.HospitalManagement.repository.UserRoleRepository;
import com.hospitalManagementSystem.HospitalManagement.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final UserRoleRepository roleRepo;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    private final AuthenticationManager authManager;

    public String register(RegisterRequest request) {
        if (userService.existsByUsername(request.getUsername()))
            throw new RuntimeException("Username already exists");

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        UserRole adminRole = roleRepo.findById("ADMIN")
                .orElse(new UserRole("ADMIN"));
        user.setRoles(Set.of(adminRole));
        userService.saveUser(user);

        return generateToken(user);

    }
    public String login(LoginRequest request) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(), request.getPassword()
        ));

        User user = userService.findByUsername(request.getUsername());
        return generateToken(user);

    }
        private String generateToken(User user) {
            UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    user.getRoles().stream()

                            .map(role -> new SimpleGrantedAuthority(role.getName()))
                            .collect(Collectors.toList())
            );
            return jwtUtil.generateToken(userDetails);
        }

    }

