package com.world.cup.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class WorldcupRepositoryTests {

    @Autowired
    private WorldcupListRepository worldcupListRepository;

    @Test
    public void testGetList(){

    }
}
