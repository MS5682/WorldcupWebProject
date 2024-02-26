package com.world.cup.service;

import com.world.cup.Proceedinterface;
import com.world.cup.dto.ChoiceDTO;
import com.world.cup.dto.SaveDTO;
import com.world.cup.entity.Choice;
import com.world.cup.entity.Proceed;

import java.util.List;

public interface ProceedService {
    void save(SaveDTO saveDTO);

    void autosave(SaveDTO saveDTO);

    void finalsave(SaveDTO saveDTO);

//    boolean havesave(String userId, int worldcupNum);

//    List<Proceedinterface> savefileload(String userId, int worldcupNum);

//    int findChoiceNum(int proceedNum);

    default Choice convertEntity(ChoiceDTO choiceDTO) {
        Choice newChoice = Choice.builder().choiceNum(choiceDTO.getChoiceNum()).build();
        return newChoice;
    }
}
