package com.hospitalManagementSystem.HospitalManagement.Entity;

import jakarta.persistence.*;

import lombok.Data;

@Entity
@Data
@Table(name = "user_role")
public class UserRole {
    @Id
    private int id;
    @Column(unique = true, nullable = false)
    private String name;

    public UserRole(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserRole(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
}
