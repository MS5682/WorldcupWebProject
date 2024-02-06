package com.world.cup.service;

import com.world.cup.dto.PageRequestDTO;
import com.world.cup.dto.PageResultDTO;
import com.world.cup.dto.WorldcupDTO;
import com.world.cup.entity.Worldcup;
import com.world.cup.repository.WorldcupListRepository;
import com.world.cup.repository.WorldcupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class WorldcupServiceImpl implements WorldcupService{

    private final WorldcupRepository worldcupRepository;

    @Override
    public PageResultDTO<WorldcupDTO, Object[]> getWorldcupList(PageRequestDTO pageRequestDTO){
        Function<Object[], WorldcupDTO> fn = ((en) -> entityToDto((Worldcup)en[0], (String) en[1],(String) en[2],
                (Byte) en[3],(Byte) en[4],(String) en[5],(String) en[6]));

        Sort sort = null;
        if (pageRequestDTO.getOrder() == 0) {
            sort = Sort.by("worldcupNum").descending();
        } else if(pageRequestDTO.getOrder() == 1) {
            sort = Sort.by("modDate").descending();
        } else if(pageRequestDTO.getOrder() == 2) {
            sort = Sort.by("viewCnt").descending();
        }

        Page<Object[]> result = worldcupRepository.getWorldcupList
                        (pageRequestDTO.getType(),
                        pageRequestDTO.getKeyword(),
                        pageRequestDTO.getPageable(sort));

        return new PageResultDTO<>(result,fn);
    }
}
