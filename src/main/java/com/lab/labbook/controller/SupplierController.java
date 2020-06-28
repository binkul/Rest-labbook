package com.lab.labbook.controller;

import com.lab.labbook.entity.dto.SupplierDto;
import com.lab.labbook.service.SupplierService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/supplier")
public class SupplierController {

    private final SupplierService service;

    @GetMapping(value = "/all")
    public List<SupplierDto> getSuppliers() {
        return service.getAll();
    }

    @GetMapping(value = "/{id}")
    public SupplierDto getSupplier(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping()
    public List<SupplierDto> getByName(@RequestParam("name") String name) {
        return service.getByName(name);
    }

    @PostMapping
    public void create(@RequestBody SupplierDto supplierDto) {
        service.create(supplierDto);
    }

    @PutMapping
    public SupplierDto update(@RequestBody SupplierDto supplierDto) {
        return service.update(supplierDto);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

}
