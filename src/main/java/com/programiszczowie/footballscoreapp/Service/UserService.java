package com.programiszczowie.footballscoreapp.Service;

import com.programiszczowie.footballscoreapp.Dto.LoginDto;
import com.programiszczowie.footballscoreapp.Dto.UserDto;
import com.programiszczowie.footballscoreapp.response.LoginResponse;
import org.springframework.http.ResponseEntity;


public interface UserService {
    ResponseEntity<String> addUser(UserDto userDto);

    ResponseEntity<LoginResponse> loginUser(LoginDto loginDto);
}
