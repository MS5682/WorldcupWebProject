package com.world.cup.repository;

import com.world.cup.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

@SpringBootTest
public class UserRepositoryTests {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void insertUser(){
        User user = User.builder()
                .id("testuser1")
                .email("testuser1@gmail.com")
                .password("1234")
                .userRole("user")
                .build();
        userRepository.save(user);
    }

    @Test
    public void getAllUsers() {
        List<User> userList = userRepository.findAll();
        for (User user : userList) {
            System.out.println("ID: " + user.getId() +
                    ", Email: " + user.getEmail() +
                    ", Password: " + user.getPassword() +
                    ", UserRole: " + user.getUserRole());
        }
    }
}
