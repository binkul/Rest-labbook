package com.lab.labbook.controller;

import com.lab.labbook.entity.clp.GhsDto;
import com.lab.labbook.entity.dto.MaterialDto;
import com.lab.labbook.service.MaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/material")
public class MaterialController {

    private final MaterialService service;

    @GetMapping(value = "/all")
    public List<MaterialDto> getMaterials() {
        return service.getAll();
    }

    @GetMapping(value = "/{id}")
    public MaterialDto getMaterial(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping()
    public List<MaterialDto> getByName(@RequestParam("name") String name) {
        return service.getByName(name);
    }

    @PostMapping
    public void create(@RequestBody MaterialDto materialDto) {
        service.create(materialDto);
    }

    @PutMapping
    public MaterialDto update(@RequestBody MaterialDto materialDto) {
        return service.update(materialDto);
    }

    @PatchMapping(value = "/{id}")
    public void updateGhs(@PathVariable Long id, @RequestBody GhsDto ghsDto) {
        service.updateGhs(id, ghsDto);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
