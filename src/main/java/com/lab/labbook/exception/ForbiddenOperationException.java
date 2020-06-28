package com.lab.labbook.exception;

public class ForbiddenOperationException extends RuntimeException {
    public ForbiddenOperationException(ExceptionType type, String value) {
        super(String.format(type.getMessage(), value));
    }
}
