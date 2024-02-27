package com.world.cup.service;

import com.world.cup.dto.ChoiceDTO;
import com.world.cup.dto.PageRequestDTO;
import com.world.cup.dto.PageResultDTO;
import com.world.cup.dto.WorldcupDTO;
import com.world.cup.entity.Choice;
import com.world.cup.entity.Worldcup;


public interface ChoiceService {
    WorldcupDTO getChoiceToWorldcup(WorldcupDTO worldcupDTO);
    WorldcupDTO getTopTen(WorldcupDTO worldcupDTO);
    WorldcupDTO getChoiceRank(WorldcupDTO worldcupDTO);
    PageResultDTO<ChoiceDTO, Object[]> getChoicePage(PageRequestDTO pageRequestDTO);

    ChoiceDTO getChoice(ChoiceDTO choiceDTO);
    Integer sumFirst(WorldcupDTO worldcupDTO);

    Integer choiceCount(int worldcupNum);
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
                .win(choiceDTO.getWin())
                .lose(choiceDTO.getLose())
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
                .win(choice.getWin())
                .lose(choice.getLose())
                .choiceNum(choice.getChoiceNum())
                .worldcupNum(choice.getWorldcup().getWorldcupNum())
                .build();
        return choiceDTO;
    }
}
