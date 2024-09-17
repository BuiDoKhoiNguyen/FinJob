package com.rs.authenticationservice.enums;

import lombok.Getter;

@Getter
public enum RoleEnum {
    ADMIN("ADMIN"),
    USER("USER");

    RoleEnum(String name) {
        this.name = name;
    }

    private final String name;
}
