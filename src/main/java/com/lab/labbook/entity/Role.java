package com.lab.labbook.entity;

import java.util.stream.Stream;

public enum Role {
    USER,
    MODERATOR,
    ADMIN;

    public static boolean exist(String role) {
        return Stream.of(Role.values())
                .map(Enum::name)
                .anyMatch(i -> i.equals(role));
    }
}
