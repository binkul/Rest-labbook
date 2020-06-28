package com.lab.labbook.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SupplierDto {
    private Long id;
    private String name;
    private String shortName;
    private String address;
    private String phones;
    private boolean producer;
    private String comments;
}
