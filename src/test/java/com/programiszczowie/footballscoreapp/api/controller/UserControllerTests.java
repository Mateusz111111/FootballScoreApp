package com.programiszczowie.footballscoreapp.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programiszczowie.footballscoreapp.Controller.UserController;
import com.programiszczowie.footballscoreapp.Dto.LoginDto;
import com.programiszczowie.footballscoreapp.Dto.UserDto;
import com.programiszczowie.footballscoreapp.Entity.User;
import com.programiszczowie.footballscoreapp.Service.UserService;
import com.programiszczowie.footballscoreapp.response.LoginResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class UserControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserController userController;
    private User user;
    private UserDto userDto;
    private LoginDto loginDto;

    @BeforeEach
    public void init() {
        user = User.builder().email("test@example.com").password("password")
                .firstName("Test").lastName("User").build();
        userDto = UserDto.builder().email("test@example.com").password("password")
                .firstName("Test").lastName("User").build();
        loginDto = LoginDto.builder()
                .email("test@example.com")
                .password("password").build();

    }

    @Test
    public void UserController_SaveUser_ReturnSaved() throws Exception {
        given(userService.addUser(ArgumentMatchers.any())).willReturn(ResponseEntity.status(HttpStatus.CREATED).body("User added successfully"));

        mockMvc.perform(post("/api/user/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isCreated())
                .andExpect(content().string("User added successfully"));

    }

    @Test
    public void UserController_SaveUser_ReturnConflict() throws Exception {
        given(userService.addUser(ArgumentMatchers.any())).willReturn(ResponseEntity.status(HttpStatus.CONFLICT).body("Email already in use"));

        mockMvc.perform(post("/api/user/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isConflict())
                .andExpect(content().string("Email already in use"));

    }

    @Test
    public void UserController_LoginUser_ReturnSuccess() throws Exception {
        LoginResponse loginResponse = new LoginResponse("Login Success", true);

        given(userService.loginUser(ArgumentMatchers.any())).willReturn(ResponseEntity.ok(loginResponse));

        mockMvc.perform(post("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Login Success"));
    }
}