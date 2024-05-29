package com.programiszczowie.footballscoreapp.api.repository;

import com.programiszczowie.footballscoreapp.Entity.User;
import com.programiszczowie.footballscoreapp.Repo.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void UserRepository_SaveAll_ReturnSavedUser() {

        User user = User.builder().email("test@example.com").password("password")
                .firstName("Test").lastName("User").build();

        User savedUser = userRepository.save(user);

        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getUserid()).isGreaterThan(0);
    }

    @Test
    public void UserRepository_FindByEmail_ReturnUser() {

        User user = User.builder().email("test@example.com").password("password")
                .firstName("Test").lastName("User").build();

        userRepository.save(user);

        User savedUser = userRepository.findByEmail("test@example.com");
        Assertions.assertThat(savedUser).isNotNull();
    }

    @Test
    public void UserRepository_GetAll_ReturnMoreThanOneUser() {

        User user = User.builder().email("test@example.com").password("password")
                .firstName("Test").lastName("User").build();

        User user2 = User.builder().email("test2@example.com").password("password")
                .firstName("Test").lastName("User").build();

        userRepository.save(user);
        userRepository.save(user2);

        List<User> userList = userRepository.findAll();

        Assertions.assertThat(userList).isNotNull();
        Assertions.assertThat(userList.size()).isEqualTo(2);
    }

    @Test
    public void UserRepository_GetUserById_ReturnUser() {

        User user = User.builder().email("test@example.com").password("password")
                .firstName("Test").lastName("User").build();

        userRepository.save(user);

        User userList = userRepository.findById(user.getUserid()).orElse(null);
        Assertions.assertThat(userList).isNotNull();
        Assertions.assertThat(userList.getUserid()).isEqualTo(user.getUserid());

    }

    @Test
    public void UserRepository_FindOneByEmailAndPassword_ReturnUser() {

        User user = User.builder().email("test@example.com").password("password")
                .firstName("Test").lastName("User").build();

        userRepository.save(user);
        Optional<User> userOptional = userRepository.findOneByEmailAndPassword("test@example.com", "password");
        Assertions.assertThat(userOptional.isPresent()).isTrue();
    }
}
