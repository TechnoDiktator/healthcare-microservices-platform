package com.pm.authservice.dto;

import com.pm.authservice.model.Role;

import java.util.UUID;

public class InternalUserRequestDTO {

    private UUID id;
    private String email;
    private String password;
    private Role role;

    // getters/setters


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