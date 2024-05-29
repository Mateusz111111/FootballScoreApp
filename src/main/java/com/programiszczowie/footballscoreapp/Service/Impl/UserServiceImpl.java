package com.programiszczowie.footballscoreapp.Service.Impl;

import com.programiszczowie.footballscoreapp.Dto.LoginDto;
import com.programiszczowie.footballscoreapp.Dto.UserDto;
import com.programiszczowie.footballscoreapp.Entity.User;
import com.programiszczowie.footballscoreapp.Repo.UserRepository;
import com.programiszczowie.footballscoreapp.Service.UserService;
import com.programiszczowie.footballscoreapp.response.LoginResponse;
import lombok.Setter;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    @Setter
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ResponseEntity<String> addUser(UserDto userDto) {
        try {
            User user = new User();
            user.setUserid(userDto.getUserid());
            user.setEmail(userDto.getEmail());
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());

            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("User added successfully");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already in use");
        }
    }

    @Override
    public ResponseEntity<LoginResponse> loginUser(LoginDto loginDto) {
        if (loginDto == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new LoginResponse("LoginDto is null", false));
        }

        User user = userRepository.findByEmail(loginDto.getEmail());
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

