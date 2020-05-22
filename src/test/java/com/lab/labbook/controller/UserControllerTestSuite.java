package com.lab.labbook.controller;

import com.google.gson.Gson;
import com.lab.labbook.entity.Role;
import com.lab.labbook.entity.dto.UserDto;
import com.lab.labbook.exception.EntityNotFoundException;
import com.lab.labbook.exception.ExceptionType;
import com.lab.labbook.facade.UserFacade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserFacade userFacade;

    @Test
    public void testGetUsers() throws Exception {
        // Given
        LocalDateTime date = LocalDateTime.now();
        UserDto userDto = new UserDto(
                1L,
                "Jacek" ,
                "Brown",
                "jbr",
                "cos@interia.pl",
                "aaaa",
                true,
                true,
                Role.ADMIN.name(),
                date);
        List<UserDto> list = new ArrayList<>();
        list.add(userDto);

        // When Then
        when(userFacade.fetchGetUsers()).thenReturn(list);
        mockMvc.perform(get("/v1/user/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Jacek")))
                .andExpect(jsonPath("$[0].lastName", is("Brown")))
                .andExpect(jsonPath("$[0].login", is("jbr")))
                .andExpect(jsonPath("$[0].email", is("cos@interia.pl")))
                .andExpect(jsonPath("$[0].password", is("aaaa")))
                .andExpect(jsonPath("$[0].blocked", is(true)))
                .andExpect(jsonPath("$[0].role", is("ADMIN")));
    }

    @Test
    public void testGetEmptyList() throws Exception {
        // Given
        List<UserDto> list = new ArrayList<>();
        when(userFacade.fetchGetUsers()).thenReturn(list);

        // When & Then
        mockMvc.perform(get("/v1/user/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void testGetUserByIdPasswordHide() throws Exception {
        // Given
        LocalDateTime date = LocalDateTime.now();
        UserDto userDto = new UserDto(
                1L,
                "Jacek" ,
                "Brown",
                "jbr",
                "cos@interia.pl",
                "****",
                true,
                true,
                Role.ADMIN.name(),
                date);

        // When Then
        when(userFacade.fetchGetById(1L, true)).thenReturn(userDto);
        mockMvc.perform(get("/v1/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Jacek")))
                .andExpect(jsonPath("$.lastName", is("Brown")))
                .andExpect(jsonPath("$.login", is("jbr")))
                .andExpect(jsonPath("$.email", is("cos@interia.pl")))
                .andExpect(jsonPath("$.password", is("****")))
                .andExpect(jsonPath("$.blocked", is(true)))
                .andExpect(jsonPath("$.role", is("ADMIN")));
    }

    @Test
    public void testGetUserThrowException() throws Exception {
        // Given
        doThrow(new EntityNotFoundException(ExceptionType.USER_NOT_FOUND, "100")).when(userFacade).fetchGetById(100L, true);

        // When & Then
        mockMvc.perform(get("/v1/user/100")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", "100"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetUserByIdPasswordOpen() throws Exception {
        // Given
        LocalDateTime date = LocalDateTime.now();
        UserDto userDto = new UserDto(
                1L,
                "Jacek" ,
                "Brown",
                "jbr",
                "cos@interia.pl",
                "aaaa",
                true,
                true,
                Role.ADMIN.name(),
                date);

        // When Then
        when(userFacade.fetchGetById(1L, false)).thenReturn(userDto);
        mockMvc.perform(get("/v1/user/ext/1")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Jacek")))
                .andExpect(jsonPath("$.lastName", is("Brown")))
                .andExpect(jsonPath("$.login", is("jbr")))
                .andExpect(jsonPath("$.email", is("cos@interia.pl")))
                .andExpect(jsonPath("$.password", is("aaaa")))
                .andExpect(jsonPath("$.blocked", is(true)))
                .andExpect(jsonPath("$.role", is("ADMIN")));
    }

    @Test
    public void testGetUserByLogin() throws Exception {
        // Given
        LocalDateTime date = LocalDateTime.now();
        UserDto userDto = new UserDto(
                1L,
                "Jacek" ,
                "Brown",
                "jbr",
                "cos@interia.pl",
                "aaaa",
                true,
                true,
                Role.ADMIN.name(),
                date);

        // When Then
        when(userFacade.fetchGetByLogin("jbr")).thenReturn(userDto);
        mockMvc.perform(get("/v1/user")
                .contentType(MediaType.APPLICATION_JSON)
                .param("login", "jbr"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Jacek")))
                .andExpect(jsonPath("$.lastName", is("Brown")))
                .andExpect(jsonPath("$.login", is("jbr")))
                .andExpect(jsonPath("$.email", is("cos@interia.pl")))
                .andExpect(jsonPath("$.password", is("aaaa")))
                .andExpect(jsonPath("$.blocked", is(true)))
                .andExpect(jsonPath("$.role", is("ADMIN")));
    }

    @Test
    public void testFindUserByName() throws Exception {
        // Given
        LocalDateTime date = LocalDateTime.now();
        UserDto userDto = new UserDto(
                1L,
                "Jacek" ,
                "Brown",
                "jbr",
                "cos@interia.pl",
                "aaaa",
                true,
                true,
                Role.ADMIN.name(),
                date);
        List<UserDto> users = new ArrayList<>();
        users.add(userDto);

        // When Then
        when(userFacade.fetchGetByName("Jacek")).thenReturn(users);
        mockMvc.perform(get("/v1/user/find")
                .contentType(MediaType.APPLICATION_JSON)
                .param("name", "Jacek"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Jacek")))
                .andExpect(jsonPath("$[0].lastName", is("Brown")))
                .andExpect(jsonPath("$[0].login", is("jbr")))
                .andExpect(jsonPath("$[0].email", is("cos@interia.pl")))
                .andExpect(jsonPath("$[0].password", is("aaaa")))
                .andExpect(jsonPath("$[0].blocked", is(true)))
                .andExpect(jsonPath("$[0].role", is("ADMIN")));
    }

    @Test
    public void testCreateUser() throws Exception {
        // Given
        UserDto userDto = new UserDto(
                1L,
                "Jacek" ,
                "Brown",
                "jbr",
                "cos@interia.pl",
                "aaaa",
                true,
                    true,
                    Role.ADMIN.name(),
                null);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(userDto);
        System.out.println(jsonContent);

        // When & Then
        mockMvc.perform(post("/v1/user")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void testUpdateUser() throws Exception {
        // Given
        UserDto userDto = new UserDto(
                1L,
                "Jacek" ,
                "Brown",
                "jbr",
                "cos@interia.pl",
                "aaaa",
                true,
                true,
                Role.ADMIN.name(),
                null);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(userDto);

        // When & Then
        when(userFacade.fetchUpdate(any(UserDto.class))).thenReturn(userDto);
        mockMvc.perform(put("/v1/user")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Jacek")))
                .andExpect(jsonPath("$.lastName", is("Brown")))
                .andExpect(jsonPath("$.login", is("jbr")))
                .andExpect(jsonPath("$.email", is("cos@interia.pl")))
                .andExpect(jsonPath("$.password", is("aaaa")))
                .andExpect(jsonPath("$.blocked", is(true)))
                .andExpect(jsonPath("$.role", is("ADMIN")));
    }

    @Test
    public void testDeleteUser() throws Exception {
        // Given
        doNothing().when(userFacade).fetchDelete(1L);

        // When Then
        mockMvc.perform(delete("/v1/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());

    }

    @Test
    public void testDeleteUserThrowException() throws Exception {
        // Given
        doThrow(new EntityNotFoundException(ExceptionType.USER_NOT_FOUND, "1")).when(userFacade).fetchDelete(100L);

        // When & Then
        mockMvc.perform(delete("/v1/user/100")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", "100"))
                .andExpect(status().isNotFound());
    }

}
