package com.world.cup.service;

import com.world.cup.dto.ChoiceDTO;
import com.world.cup.dto.SaveDTO;
import com.world.cup.entity.Choice;
import com.world.cup.entity.Worldcup;
import com.world.cup.repository.WorldcupRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface ProceedService {
    void save(SaveDTO saveDTO);

    void autosave(SaveDTO saveDTO);

    void finalsave(SaveDTO saveDTO);

    void nologinsave(SaveDTO saveDTO);

    boolean havesave(String userId, int worldcupNum);

    List<Choice> savefileload(String userId, int worldcupNum);

    int[] round(String userId, int worldcupNum);

    void savedelete(String userId, int worldcupNum);

    default Choice convertEntity(ChoiceDTO choiceDTO) {
        Choice newChoice = Choice.builder().choiceNum(choiceDTO.getChoiceNum()).build();
        return newChoice;
    }

    default Choice convertDTO(ChoiceDTO choiceDTO, WorldcupRepository worldcupRepository, int worldcupNum) {

        Worldcup worldcup = (Worldcup) worldcupRepository.getWorldcupByWorldcupNum(worldcupNum);

        Choice c = Choice.builder()
                .choiceNum(choiceDTO.getChoiceNum())
                .first(choiceDTO.getFirst())
                .name(choiceDTO.getName())
                .type(choiceDTO.getType())
                .worldcup(worldcup)
                .imgName(choiceDTO.getImgName())
                .path(choiceDTO.getPath())
                .uuid(choiceDTO.getUuid())
                .win(choiceDTO.getWin())
                .lose(choiceDTO.getLose())
                .build();

        return c;
    }
}
