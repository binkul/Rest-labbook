package com.lab.labbook.exception;

public class EntityAlreadyExistsException extends RuntimeException {
    public EntityAlreadyExistsException(ExceptionType type, String value) {
        super(String.format(type.getMessage(), value));
    }
}
