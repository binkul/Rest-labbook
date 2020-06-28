package com.lab.labbook.controller;

import com.lab.labbook.entity.dto.SeriesDto;
import com.lab.labbook.service.SeriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/series")
public class SeriesController {

    private final SeriesService service;

    @GetMapping(value = "/all")
    public List<SeriesDto> getAll() {
        return service.getAll();
    }

    @GetMapping(value = "/{id}")
    public SeriesDto getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public List<SeriesDto> getByName(@RequestParam("title") String title) {
        return service.getByTitle(title);
    }

    @PostMapping
    public void create(@RequestBody SeriesDto seriesDto) {
        service.create(seriesDto);
    }

    @PutMapping
    public SeriesDto update(@RequestBody SeriesDto seriesDto) {
        return service.update(seriesDto);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
