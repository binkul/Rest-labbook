package com.lab.labbook.controller;

import com.lab.labbook.entity.Log;
import com.lab.labbook.service.LogService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/log")
public class LogController {

    private final LogService service;

    @GetMapping
    public List<Log> getSuppliers() {
        return service.getAll();
    }
}
