package com.hospitalManagementSystem.HospitalManagement.service;


import com.hospitalManagementSystem.HospitalManagement.Entity.UserRole;
import com.hospitalManagementSystem.HospitalManagement.Entity.User;
import com.hospitalManagementSystem.HospitalManagement.dto.LoginRequest;
import com.hospitalManagementSystem.HospitalManagement.dto.RegisterRequest;
import com.hospitalManagementSystem.HospitalManagement.exception.UsernameAlreadyExistsException;
import com.hospitalManagementSystem.HospitalManagement.repository.UserRepository;
import com.hospitalManagementSystem.HospitalManagement.repository.UserRoleRepository;
import com.hospitalManagementSystem.HospitalManagement.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final UserRoleRepository roleRepo;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authManager;
    private final UserRepository userRepository;

    public String register(RegisterRequest request) {
        if (userService.existsByUsername(request.getUsername()))
            throw new UsernameAlreadyExistsException("Username already exists");

        // create User object (step: 1)
        User user = new User();
        user.setFullName(request.getFullname());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // save user first(without roles) step:2

        user = userRepository.save(user);

        // Assign roles
        UserRole adminRole = roleRepo.findByName("ROLE_ADMIN")
                        .orElseGet(() -> roleRepo.save(new UserRole("ROLE_ADMIN")));
        Set<UserRole> roles = new HashSet<>();
        roles.add(adminRole);
        user.setRoles(roles);

        // Save user again with roles

        user = userRepository.save(user);

        return generateToken(user);


    }
    public String login(LoginRequest request) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(), request.getPassword()
        ));

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
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
            return jwtService.generateToken(userDetails);
        }

    }

