package com.lab.labbook.exception.advice;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class LabBookException {
    private LocalDateTime date;
    private int status;
    private String message;
}
