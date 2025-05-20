package com.hospitalManagementSystem.HospitalManagement.service;

import com.hospitalManagementSystem.HospitalManagement.Entity.User;

import java.util.List;

public interface UserService {
    User findByUsername(String username);
    User saveUser(User user);
    List<User> getAllUsers();

    void deleteUser(User user);
    boolean existsByUsername(String username);

    }

