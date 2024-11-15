package com.example.springaop_hw2.model.enums;

import lombok.Getter;

@Getter
public enum Role {

    ROLE_ADMIN("admin"), ROLE_USER("user");

    private final String role;

    Role(String role) {
        this.role = role;
    }
}