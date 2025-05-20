package com.hospitalManagementSystem.HospitalManagement.Entity;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_role")
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String name;



    public UserRole(String name){
     this.name = name.startsWith("ROLE_") ? name.toUpperCase() : "ROLE_" + name.toUpperCase();
    }
}
