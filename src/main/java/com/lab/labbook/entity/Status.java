package com.lab.labbook.entity;

import java.util.stream.Stream;

public enum Status {
    NONE,
    MODIFIED,
    CREATED,
    DELETED;

    public static boolean exist(String status) {
        return Stream.of(Status.values())
                .map(Enum::name)
                .anyMatch(i -> i.equals(status));
    }
}
