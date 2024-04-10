package com.programiszczowie.footballscoreapp.Controller;


import com.programiszczowie.footballscoreapp.Dto.LoginDto;
import com.programiszczowie.footballscoreapp.Dto.UserDto;
import com.programiszczowie.footballscoreapp.Service.UserService;
import com.programiszczowie.footballscoreapp.response.LoginResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String hello() {
        return "Hello World";
    }

    private final UserService userService;

    @PostMapping("/save")
    public String saveUser(@RequestBody UserDto userDto) {

        return userService.addUser(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDto loginDto) {

        LoginResponse loginResponse = userService.loginUser(loginDto);
        return ResponseEntity.ok().body(loginResponse);
    }
}