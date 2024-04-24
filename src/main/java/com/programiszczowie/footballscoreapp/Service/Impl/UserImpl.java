package com.programiszczowie.footballscoreapp.Service.Impl;

import com.programiszczowie.footballscoreapp.Dto.LoginDto;
import com.programiszczowie.footballscoreapp.Dto.UserDto;
import com.programiszczowie.footballscoreapp.Entity.User;
import com.programiszczowie.footballscoreapp.Repo.UserRepo;
import com.programiszczowie.footballscoreapp.Service.UserService;
import com.programiszczowie.footballscoreapp.response.LoginResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserImpl implements UserService {

    private final UserRepo userRepo;

    private final PasswordEncoder passwordEncoder;

    public UserImpl(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String addUser(UserDto userDto) {

        User user = new User(
                userDto.getUserid(),
                userDto.getEmail(),
                userDto.getFirstName(),
                userDto.getLastName(),
                this.passwordEncoder.encode(userDto.getPassword())
        );

        userRepo.save(user);

        return user.getEmail();
    }

    public ResponseEntity<LoginResponse> loginUser(LoginDto loginDto) {
        if (loginDto == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new LoginResponse("LoginDto is null", false));
        }

        User user = userRepo.findByEmail(loginDto.getEmail());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse("Email doesn't exist", false));
        }

        String password = loginDto.getPassword();
        String encodedPassword = user.getPassword();
        boolean isPasswordCorrect = passwordEncoder.matches(password, encodedPassword);

        if (!isPasswordCorrect) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse("Password doesn't match", false));
        }

        return ResponseEntity.ok(new LoginResponse("Login Success", true));
    }

}
