package com.world.cup.service;

import com.world.cup.dto.WorldcupDTO;
import com.world.cup.entity.Worldcup;
import com.world.cup.repository.WorldcupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class WorldcupServiceImpl implements WorldcupService{

    private final WorldcupRepository worldcupRepository;

    @Override
    public List<WorldcupDTO> getWorldcupList(){

        List<Object[]> result = worldcupRepository.getWorldcupWithChoices();
        log.info("sql result :" + result );
        List<WorldcupDTO> worldcupDTOList = new ArrayList<>();

        for (Object[] row : result) {
            Worldcup worldcup = (Worldcup) row[0];
            String name1 = (String) row[1];
            Byte type1 = (Byte) row[2];
            String link1 = (String) row[3];
            String name2 = (String) row[4];
            Byte type2 = (Byte) row[5];
            String link2 = (String) row[6];

            WorldcupDTO worldcupDTO = entityToDto(worldcup, name1, name2, type1, type2, link1, link2);
            worldcupDTOList.add(worldcupDTO);
        }

        return worldcupDTOList;
    }
}
