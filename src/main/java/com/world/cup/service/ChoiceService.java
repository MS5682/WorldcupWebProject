package com.world.cup.service;

import com.world.cup.dto.ChoiceDTO;
import com.world.cup.dto.WorldcupDTO;
import com.world.cup.entity.Choice;
import com.world.cup.entity.Worldcup;


public interface ChoiceService {
    WorldcupDTO getChoiceToWorldcup(WorldcupDTO worldcupDTO);

    void addChoice(ChoiceDTO choiceDTO);

    void modifyChoiceName(ChoiceDTO choiceDTO);

    void modifyChoiceImg(ChoiceDTO choiceDTO);

    void modifyChoiceVideo(ChoiceDTO choiceDTO);

    void deleteChoice(ChoiceDTO choiceDTO);

    default Choice dtoToEntity(ChoiceDTO choiceDTO){
        Worldcup worldcup = Worldcup.builder()
                .worldcupNum(choiceDTO.getWorldcupNum())
                .build();
        Choice choice = Choice.builder()
                .name(choiceDTO.getName())
                .choiceNum(choiceDTO.getChoiceNum())
                .first(choiceDTO.getChoiceNum())
                .path(choiceDTO.getPath())
                .uuid(choiceDTO.getUuid())
                .imgName(choiceDTO.getImgName())
                .type(choiceDTO.getType())
                .worldcup(worldcup)
                .build();
        return choice;
    }

    default ChoiceDTO entityToDto(Choice choice){
        ChoiceDTO choiceDTO = ChoiceDTO.builder()
                .choiceNum(choice.getChoiceNum())
                .first(choice.getFirst())
                .imgName(choice.getImgName())
                .path(choice.getPath())
                .uuid(choice.getUuid())
                .name(choice.getName())
                .type(choice.getType())
                .choiceNum(choice.getChoiceNum())
                .worldcupNum(choice.getWorldcup().getWorldcupNum())
                .build();
        return choiceDTO;
    }
}
