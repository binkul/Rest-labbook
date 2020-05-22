package com.lab.labbook.controller;

import com.lab.labbook.entity.dto.LabBookDto;
import com.lab.labbook.entity.extended.LabBookExtDto;
import com.lab.labbook.service.LabBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/lab")
public class LabBookController {

    private final LabBookService service;

    @GetMapping(value = "/all")
    public List<LabBookExtDto> getAll() {
        return service.getAll();
    }

    @GetMapping
    public List<LabBookExtDto> getByTitle(@RequestParam("title") String title) {
        return service.getByTitle(title);
    }

    @GetMapping(value = "/user/{userId}/title")
    public List<LabBookExtDto> getByUserAndTitle(@PathVariable Long userId, @RequestParam String title) {
        return service.getByUserAndTitle(userId, title);
    }

    @GetMapping(value = "/user/{userId}")
    public List<LabBookExtDto> getByUser(@PathVariable Long userId) {
        return service.getByUser(userId);
    }

    @GetMapping(value = "/{id}")
    public LabBookExtDto getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public void create(@RequestBody LabBookDto labBookDto) {
        service.create(labBookDto);
    }

    @PatchMapping(value = "/{labId}")
    public LabBookExtDto update(@RequestBody Map<String, String> updates, @PathVariable Long labId) {
        return service.update(updates, labId);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
