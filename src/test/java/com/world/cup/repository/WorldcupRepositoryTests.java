package com.world.cup.repository;

import com.world.cup.dto.WorldcupDTO;
import com.world.cup.entity.Worldcup;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class WorldcupRepositoryTests {

    @Autowired
    private WorldcupRepository worldcupRepository;

    @Test
    public void testGetList(){
        List<Object[]> result = worldcupRepository.getWorldcupWithChoices();
        System.out.println("sql result :" + result );

    }
}
