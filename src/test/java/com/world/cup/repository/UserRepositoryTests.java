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
                .id("testuser10")
                .email("testuser10@gmail.com")
                .password("1234")
                .userRole("user")
                .build();
        userRepository.save(user);
    }

}
