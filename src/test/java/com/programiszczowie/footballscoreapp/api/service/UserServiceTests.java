package com.programiszczowie.footballscoreapp.api.service;

import com.programiszczowie.footballscoreapp.Dto.LoginDto;
import com.programiszczowie.footballscoreapp.Dto.UserDto;
import com.programiszczowie.footballscoreapp.Entity.User;
import com.programiszczowie.footballscoreapp.Repo.UserRepository;
import com.programiszczowie.footballscoreapp.Service.Impl.UserServiceImpl;
import com.programiszczowie.footballscoreapp.response.LoginResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void UserService_AddUser_ReturnsUserDto() {
        User user = User.builder().email("test@example.com").password("password")
                .firstName("Test").lastName("User").build();
        UserDto userDto = UserDto.builder().email("test@example.com").password("password")
                .firstName("Test").lastName("User").build();

        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        userService.setPasswordEncoder(passwordEncoder);

        ResponseEntity<String> savedUser = userService.addUser(userDto);

        Assertions.assertThat(savedUser).isNotNull();
    }

    @Test
    public void testLoginUser_ReturnsSuccess() {
        LoginDto loginDto = new LoginDto("test@example.com", "password");
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword(passwordEncoder.encode("password"));

        when(userRepository.findByEmail("test@example.com")).thenReturn(user);

        when(passwordEncoder.matches("password", user.getPassword())).thenReturn(true);

        ResponseEntity<LoginResponse> responseEntity = userService.loginUser(loginDto);

        assertThat(Objects.requireNonNull(responseEntity.getBody()).getMessage()).isEqualTo("Login Success");
        assertThat(responseEntity.getBody().getStatus()).isTrue();
    }

    @Test
    public void testLoginUser_NullLoginDto() {
        ResponseEntity<LoginResponse> responseEntity = userService.loginUser(null);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        assertThat(Objects.requireNonNull(responseEntity.getBody()).getMessage()).isEqualTo("LoginDto is null");
        assertThat(responseEntity.getBody().getStatus()).isFalse();
    }

    @Test
    public void testLoginUser_IncorrectPassword() {
        LoginDto loginDto = new LoginDto("test@example.com", "wrongpassword");
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword(passwordEncoder.encode("password"));

        when(userRepository.findByEmail("test@example.com")).thenReturn(user);

        when(passwordEncoder.matches("wrongpassword", user.getPassword())).thenReturn(false);

        ResponseEntity<LoginResponse> responseEntity = userService.loginUser(loginDto);

        assertThat(Objects.requireNonNull(responseEntity.getBody()).getMessage()).isEqualTo("Password doesn't match");
        assertThat(responseEntity.getBody().getStatus()).isFalse();
    }
}
