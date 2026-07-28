package com.pm.doctorservice.security;

import com.pm.doctorservice.enums.Role;

public class UserContext {

    private final String userId;
    private final String email;
    private final Role role;

    public UserContext(String userId,
                       String email,
                       Role role) {

        this.userId = userId;
        this.email = email;
        this.role = role;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }
}