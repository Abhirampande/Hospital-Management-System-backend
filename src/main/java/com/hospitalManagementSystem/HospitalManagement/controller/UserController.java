package com.hospitalManagementSystem.HospitalManagement.controller;

import com.hospitalManagementSystem.HospitalManagement.Entity.User;
import com.hospitalManagementSystem.HospitalManagement.Entity.UserRole;
import com.hospitalManagementSystem.HospitalManagement.dto.UserRequest;
import com.hospitalManagementSystem.HospitalManagement.repository.UserRoleRepository;
import com.hospitalManagementSystem.HospitalManagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.Role;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor

public class UserController {
    private final UserService userService;
    private final UserRoleRepository userRoleRepository;
    //@PreAuthorize can only be used when @EnableMethodSecurity in a config(security config) is active


    // Get All Users (Admin only)
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // Get User By Username(Admin only)
    @GetMapping("/username")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username){
        return ResponseEntity.ok(userService.findByUsername(username));
    }

    //Create new User(Admin)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> createUser(@RequestBody UserRequest userRequest ){
        if (userService.existsByUsername(userRequest.getUsername())){
                return ResponseEntity.badRequest().build();
        }
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());

        Set<UserRole> roles = new HashSet<>();
        for(String roleName : userRequest.getRoles()){
            UserRole role = userRoleRepository.findByName(roleName)
                    .orElseThrow(()-> new RuntimeException("Role not found: " + roleName));
            roles.add(role);
        }
        user.setRoles(roles);

        User savedUser = userService.saveUser(user);
        return ResponseEntity.ok(savedUser);
    }

    //update existing user(Admin only)
    public ResponseEntity<User> updateUser(@PathVariable String username, @RequestBody UserRequest userRequest) {
        User existingUser = userService.findByUsername(username);

        existingUser.setEmail(userRequest.getEmail());
        if (
                userRequest.getPassword() != null && !userRequest.getPassword().isEmpty()) {
            existingUser.setPassword(userRequest.getPassword()); // need to encode it in service layer with password encoder
        }

        Set<UserRole> roles = new HashSet<>();
        for (String roleName : userRequest.getRoles()) {
            UserRole role = userRoleRepository.findByName(roleName)
                    .orElseThrow(() -> new RuntimeException("Role not found : " + roleName));
            roles.add(role);
        }
        existingUser.setRoles(roles);

        User updatedUser = userService.saveUser(existingUser);
        return ResponseEntity.ok(updatedUser);

    }

    //Delete user by username(Admin only)
    @DeleteMapping("/{username}")
    @PreAuthorize("hasRole('AADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable String username){
        User user = userService.findByUsername(username);
        userService.deleteUser(user);
        return ResponseEntity.noContent().build();
    }

}
