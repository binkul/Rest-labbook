package com.lab.labbook.controller;

import com.lab.labbook.entity.dto.CurrencyRateDto;
import com.lab.labbook.service.CurrencyRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/currency")
public class CurrencyRateController {

    private final CurrencyRateService service;

    @GetMapping
    public List<CurrencyRateDto> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public CurrencyRateDto getById(@PathVariable Long id) {
        return service.getById(id);
    }
}
