package com.world.cup.repository;

import com.world.cup.dto.ChoiceDTO;
import com.world.cup.dto.PageRequestDTO;
import com.world.cup.dto.PageResultDTO;
import com.world.cup.dto.WorldcupDTO;
import com.world.cup.entity.Worldcup;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

@SpringBootTest
public class WorldcupRepositoryTests {

    @Autowired
    private WorldcupRepository worldcupRepository;

    @Test
    public void testGetList(){
        PageRequestDTO pageRequestDTO = new PageRequestDTO();
        pageRequestDTO.setPage(1);
        pageRequestDTO.setSize(9);
        String type = null;
        String keyword = null;
        Pageable pageable = pageRequestDTO.getPageable(Sort.by("worldcupNum").descending());
        String userId = null;
        Byte disclosure = null;
        Page<Object[]> result =  worldcupRepository.getWorldcupList(type, keyword, pageable, userId, disclosure);
        System.out.println(result);
        for(Object[] objects: result.getContent()){
            System.out.println(objects);
        }
    }
}
