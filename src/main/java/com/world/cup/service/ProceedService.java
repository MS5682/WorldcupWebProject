package com.world.cup.service;

import com.world.cup.dto.ChoiceDTO;
import com.world.cup.dto.SaveDTO;
import com.world.cup.entity.Choice;

import java.util.List;

public interface ProceedService {
    void save(SaveDTO saveDTO);

    void autosave(SaveDTO saveDTO);

    void finalsave(SaveDTO saveDTO);

    void nologinsave(Choice c, Choice[] choices);

    boolean havesave(String userId, int worldcupNum);

    List<Choice> savefileload(String userId, int worldcupNum);

    int[] round(String userId, int worldcupNum);

    void savedelete(String userId, int worldcupNum);

    default Choice convertEntity(ChoiceDTO choiceDTO) {
        Choice newChoice = Choice.builder().choiceNum(choiceDTO.getChoiceNum()).build();
        return newChoice;
    }
}
