package com.lab.labbook.exception.advice;

import com.lab.labbook.exception.EntityAlreadyExistsException;
import com.lab.labbook.exception.EntityNotFoundException;
import com.lab.labbook.exception.ForbiddenOperationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public LabBookException readerNotFoundException(EntityNotFoundException ex) {
        return new LabBookException(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public LabBookException readerAlreadyExistsException(EntityAlreadyExistsException ex) {
        return new LabBookException(LocalDateTime.now(), HttpStatus.FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler(ForbiddenOperationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public LabBookException readerForbiddenOperationException(ForbiddenOperationException ex) {
        return new LabBookException(LocalDateTime.now(), HttpStatus.FORBIDDEN.value(), ex.getMessage());
    }
}
