package com.world.cup.service;

import com.world.cup.dto.ChoiceDTO;
import com.world.cup.dto.WorldcupDTO;
import com.world.cup.entity.Choice;
import com.world.cup.entity.Worldcup;


public interface ChoiceService {
    WorldcupDTO addChoiceToWorldcup(WorldcupDTO worldcupDTO);
    default Choice dtoToEntity(ChoiceDTO choiceDTO){
        Worldcup worldcup = Worldcup.builder()
                .worldcupNum(choiceDTO.getWorldcupNum())
                .build();
        Choice choice = Choice.builder()
                .name(choiceDTO.getName())
                .choiceNum(choiceDTO.getChoiceNum())
                .first(choiceDTO.getChoiceNum())
                .link(choiceDTO.getLink())
                .type(choiceDTO.getType())
                .worldcup(worldcup)
                .build();
        return choice;
    }

    default ChoiceDTO entityToDto(Choice choice){
        ChoiceDTO choiceDTO = ChoiceDTO.builder()
                .choiceNum(choice.getChoiceNum())
                .first(choice.getFirst())
                .link(choice.getLink())
                .name(choice.getName())
                .type(choice.getType())
                .choiceNum(choice.getChoiceNum())
                .worldcupNum(choice.getWorldcup().getWorldcupNum())
                .build();
        return choiceDTO;
    }
}
