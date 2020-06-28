package com.lab.labbook.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    private Long id;
    private String name;
    private String lastName;
    private String login;
    private String email;
    private String password;
    private boolean blocked;
    private boolean observer;
    private String role;
    private LocalDateTime date;
}
