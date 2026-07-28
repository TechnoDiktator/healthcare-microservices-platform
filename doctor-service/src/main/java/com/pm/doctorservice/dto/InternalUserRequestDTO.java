package com.pm.doctorservice.dto;

import com.pm.doctorservice.enums.Role;

import java.util.UUID;

public class InternalUserRequestDTO {

    private UUID id;
    private String email;
    private String password;
    private Role role;

    public InternalUserRequestDTO() {
    }

    public InternalUserRequestDTO(UUID id,
                                  String email,
                                  String password,
                                  Role role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}