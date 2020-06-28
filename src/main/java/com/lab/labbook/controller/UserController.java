package com.lab.labbook.controller;

import com.lab.labbook.entity.dto.UserDto;
import com.lab.labbook.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/user")
public class UserController {

    private final UserFacade userFacade;

    @GetMapping(value = "/all")
    public List<UserDto> getAll() {
        return userFacade.fetchGetUsers();
    }

    @GetMapping(value = "/{id}")
    public UserDto getHidePassword(@PathVariable Long id) {
        return userFacade.fetchGetById(id, true);
    }

    @GetMapping(value = "ext/{id}")
    public UserDto getOpenPassword(@PathVariable Long id) {
        return userFacade.fetchGetById(id, false);
    }

    @GetMapping
    public UserDto getByLogin(@RequestParam("login") String login) {
        return userFacade.fetchGetByLogin(login);
    }

    @GetMapping(value = "/find")
    public List<UserDto> getByName(@RequestParam("name") String name) {
        return userFacade.fetchGetByName(name);
    }

    @PostMapping
    public void create(@RequestBody UserDto userDto) {
        userFacade.fetchCreate(userDto);
    }

    @PutMapping
    public UserDto update(@RequestBody UserDto userDto) {
        return userFacade.fetchUpdate(userDto);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Long id) {
        userFacade.fetchDelete(id);
    }
}
