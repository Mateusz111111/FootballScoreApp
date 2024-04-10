package com.programiszczowie.footballscoreapp.Service.Impl;

import com.programiszczowie.footballscoreapp.Dto.LoginDto;
import com.programiszczowie.footballscoreapp.Dto.UserDto;
import com.programiszczowie.footballscoreapp.Entity.User;
import com.programiszczowie.footballscoreapp.Repo.UserRepo;
import com.programiszczowie.footballscoreapp.Service.UserService;
import com.programiszczowie.footballscoreapp.response.LoginResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    @Override
    public LoginResponse loginUser(LoginDto loginDto) {

        User user1 = userRepo.findByEmail(loginDto.getEmail());
        if (user1 != null) {
            String password = loginDto.getPassword();
            String encodedPassword = user1.getPassword();
            boolean isPasswordCorrect = passwordEncoder.matches(password, encodedPassword);

            if (isPasswordCorrect) {
                Optional<User> user = userRepo.findOneByEmailAndPassword(loginDto.getEmail(), encodedPassword);
                if (user.isPresent()) {
                    return new LoginResponse("Login Success", true);
                } else {
                    return new LoginResponse("Login Failure", false);
                }
            } else {
                return new LoginResponse("Password don't match", false);
            }
        } else {
            return new LoginResponse("Email don't exist", false);
        }
    }
}
