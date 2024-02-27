package com.world.cup.service;

import com.world.cup.dto.PageRequestDTO;
import com.world.cup.dto.PageResultDTO;
import com.world.cup.dto.WorldcupDTO;
import com.world.cup.entity.Choice;
import com.world.cup.entity.Worldcup;
import com.world.cup.repository.WorldcupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class WorldcupServiceImpl implements WorldcupService{

    private final WorldcupRepository worldcupRepository;

    @Override
    public PageResultDTO<WorldcupDTO, Object[]> getWorldcupList(PageRequestDTO pageRequestDTO){
        Function<Object[], WorldcupDTO> fn = ((en) -> entityToDto((Worldcup)en[0], (String) en[1],(String) en[2],
                (Byte) en[3],(Byte) en[4],(String) en[5],(String) en[6],(String) en[7],(String) en[8],
                (String) en[9],(String) en[10]));

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
                        pageRequestDTO.getPageable(sort),
                                pageRequestDTO.getUserId(),
                                pageRequestDTO.getDisclosure());

        return new PageResultDTO<>(result,fn);
    }

    @Override
    public PageResultDTO<WorldcupDTO, Object[]> getProceedWorldcupList(PageRequestDTO pageRequestDTO){
        Function<Object[], WorldcupDTO> fn = ((en) -> entityToDto((Worldcup)en[0], (String) en[1],(String) en[2],
                (Byte) en[3],(Byte) en[4],(String) en[5],(String) en[6],(String) en[7],(String) en[8],
                (String) en[9],(String) en[10]));

        Sort sort = Sort.by("worldcupNum").descending();

        Page<Object[]> result = worldcupRepository.getProceedWorldcupList
                (
                        pageRequestDTO.getPageable(sort),
                        pageRequestDTO.getUserId()
                        );

        return new PageResultDTO<>(result,fn);
    }

    @Override
    public int register(WorldcupDTO worldcupDTO) {
        log.info(worldcupDTO);
        Worldcup worldcup = dtoToEntity(worldcupDTO);
        log.info(worldcup);
        worldcupRepository.save(worldcup);
        return worldcup.getWorldcupNum();
    }

    @Override
    public WorldcupDTO getWorldcup(WorldcupDTO worldcupDTO) {
        Object result = worldcupRepository.getWorldcupByWorldcupNum(worldcupDTO.getWorldcupNum());
        return entityToDto((Worldcup)result);
    }

    @Override
    public void modifyWorldcup(WorldcupDTO worldcupDTO) {
        Worldcup worldcup = worldcupRepository.getOne(worldcupDTO.getWorldcupNum());
        if(worldcup != null){
            worldcup.changeDescription(worldcupDTO.getDescription());
            worldcup.changeDisclosure(worldcupDTO.getDisclosure());
            worldcup.changeTitle(worldcupDTO.getTitle());
        }
        worldcupRepository.save(worldcup);
    }

    @Override
    public PageResultDTO<WorldcupDTO, Object[]> getPublicWorldcupList(PageRequestDTO pageRequestDTO) {
        Function<Object[], WorldcupDTO> fn = ((en) -> entityToDto((Worldcup)en[0], (String) en[1],(String) en[2],
                (Byte) en[3],(Byte) en[4],(String) en[5],(String) en[6],(String) en[7],(String) en[8],
                (String) en[9],(String) en[10]));
        Sort sort = null;
        sort = Sort.by("regDate").descending();
        Page<Object[]> result = worldcupRepository.getPublicWorldcupList
                (pageRequestDTO.getType(),
                        pageRequestDTO.getKeyword(),
                        pageRequestDTO.getPageable(sort));

        return new PageResultDTO<>(result,fn);
    }

    @Override
    public PageResultDTO<WorldcupDTO, Object[]> getPrivateWorldcupList(PageRequestDTO pageRequestDTO) {
        Function<Object[], WorldcupDTO> fn = ((en) -> entityToDto((Worldcup)en[0], (String) en[1],(String) en[2],
                (Byte) en[3],(Byte) en[4],(String) en[5],(String) en[6],(String) en[7],(String) en[8],
                (String) en[9],(String) en[10]));
        Sort sort = null;
        sort = Sort.by("regDate").descending();
        Page<Object[]> result = worldcupRepository.getPrivateWorldcupList
                (pageRequestDTO.getType(),
                        pageRequestDTO.getKeyword(),
                        pageRequestDTO.getPageable(sort));

        return new PageResultDTO<>(result,fn);
    }

    @Override
    public void updateDisclousre(int worldcupNum) {
        worldcupRepository.updateDisclosureByWorldcupNum(worldcupNum);

    }

    @Override
    @Transactional
    public void deleteWorldcup(WorldcupDTO worldcupDTO) {
        worldcupRepository.deleteById(worldcupDTO.getWorldcupNum());
    }

    @Override
    public void updateViewCnt(WorldcupDTO worldcupDTO) {
        Worldcup worldcup = worldcupRepository.getOne(worldcupDTO.getWorldcupNum());
        if(worldcup != null){
            worldcup.changeViewCnt(worldcup.getViewCnt() + 1);
        }
        worldcupRepository.save(worldcup);
    }


}
