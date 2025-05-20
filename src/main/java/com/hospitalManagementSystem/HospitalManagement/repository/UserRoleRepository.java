package com.hospitalManagementSystem.HospitalManagement.repository;

import com.hospitalManagementSystem.HospitalManagement.Entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRoleRepository extends JpaRepository<UserRole,String> {
    @Override
    Optional<UserRole> findById(String name);
}
