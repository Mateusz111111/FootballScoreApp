package com.programiszczowie.footballscoreapp.Service;

import com.programiszczowie.footballscoreapp.Dto.LoginDto;
import com.programiszczowie.footballscoreapp.Dto.UserDto;
import com.programiszczowie.footballscoreapp.response.LoginResponse;


public interface UserService {

    String addUser(UserDto userDto);

    LoginResponse loginUser(LoginDto loginDto);
}
