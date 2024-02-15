package com.world.cup.service;

import com.world.cup.dto.ChoiceDTO;
import com.world.cup.dto.WorldcupDTO;
import com.world.cup.entity.Choice;
import com.world.cup.repository.ChoiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ChoiceServiceImpl implements ChoiceService {

    private final ChoiceRepository choiceRepository;

    @Override
    public WorldcupDTO getChoiceToWorldcup(WorldcupDTO worldcupDTO) {
        int worldcupNum = worldcupDTO.getWorldcupNum();
        List<Choice> choices = choiceRepository.getChoiceByWorldcupNum(worldcupNum);

        List<ChoiceDTO> choiceDTOs = choices.stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());

        worldcupDTO.setChoice(choiceDTOs);

        return worldcupDTO;
    }

    @Override
    public void addChoice(ChoiceDTO choiceDTO) {
        Choice choice = dtoToEntity(choiceDTO);
        choiceRepository.save(choice);
    }

    @Override
    @Transactional
    public void modifyChoiceName(ChoiceDTO choiceDTO) {
        Choice choice = choiceRepository.getOne(choiceDTO.getChoiceNum());
        if(choice != null){
            choice.changeName(choiceDTO.getName());
        }
        choiceRepository.save(choice);
    }

    @Override
    @Transactional
    public void modifyChoiceImg(ChoiceDTO choiceDTO) {
        Choice choice = choiceRepository.getOne(choiceDTO.getChoiceNum());
        if(choice != null){
            choice.changeImgName(choiceDTO.getImgName());
            choice.changePath(choiceDTO.getPath());
            choice.changeUuid(choiceDTO.getUuid());
        }
        choiceRepository.save(choice);
    }

    @Override
    @Transactional
    public void modifyChoiceVideo(ChoiceDTO choiceDTO) {
        Choice choice = choiceRepository.getOne(choiceDTO.getChoiceNum());
        if(choice != null){
            choice.changePath(choiceDTO.getPath());
            choice.changeImgName(choiceDTO.getImgName());
        }
        log.info(choiceDTO);
        log.info(choice);
        choiceRepository.save(choice);
    }

    @Override
    public void deleteChoice(ChoiceDTO choiceDTO) {
        choiceRepository.deleteById(choiceDTO.getChoiceNum());
    }


}
