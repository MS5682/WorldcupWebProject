package com.world.cup.service;

import com.world.cup.dto.ChoiceDTO;
import com.world.cup.dto.PageRequestDTO;
import com.world.cup.dto.PageResultDTO;
import com.world.cup.dto.WorldcupDTO;
import com.world.cup.entity.User;
import com.world.cup.entity.Worldcup;

import java.util.ArrayList;
import java.util.List;


public interface WorldcupService {

    PageResultDTO<WorldcupDTO, Object[]> getWorldcupList(PageRequestDTO pageRequestDTO);

    int register(WorldcupDTO worldcupDTO);

    WorldcupDTO getWorldcup(WorldcupDTO worldcupDTO);

    void modifyWorldcup(WorldcupDTO worldcupDTO);

    PageResultDTO<WorldcupDTO, Object[]> getPublicWorldcupList(PageRequestDTO pageRequestDTO);

    PageResultDTO<WorldcupDTO, Object[]> getPrivateWorldcupList(PageRequestDTO pageRequestDTO);

    void updateDisclousre(int worldcupNum);

    void deleteWorldcup(WorldcupDTO worldcupDTO);


    default Worldcup dtoToEntity(WorldcupDTO worldcupDTO){
        User user = User.builder()
                .id(worldcupDTO.getId())
                .build();
        Worldcup worldcup = Worldcup.builder()
                .worldcupNum(worldcupDTO.getWorldcupNum())
                .title(worldcupDTO.getTitle())
                .description(worldcupDTO.getDescription())
                .disclosure(worldcupDTO.getDisclosure())
                .user(user)
                .build();
        return worldcup;
    }
    default WorldcupDTO entityToDto(Worldcup worldcup){
        WorldcupDTO worldcupDTO = WorldcupDTO.builder()
                .worldcupNum(worldcup.getWorldcupNum())
                .description(worldcup.getDescription())
                .disclosure(worldcup.getDisclosure())
                .modDate(worldcup.getModDate())
                .regDate(worldcup.getRegDate())
                .title(worldcup.getTitle())
                .build();
        return worldcupDTO;
    }
    default WorldcupDTO entityToDto(Worldcup worldcup,String name1,String name2,
                                    Byte type1,Byte type2,
                                    String uuid1,String uuid2,
                                    String imgName1,String imgName2,
                                    String path1,String path2){

        List<ChoiceDTO> choices = new ArrayList<>();
        choices.add(ChoiceDTO.builder().name(name1).type(type1).path(path1).uuid(uuid1).imgName(imgName1).build());
        choices.add(ChoiceDTO.builder().name(name2).type(type2).path(path2).uuid(uuid2).imgName(imgName2).build());

        WorldcupDTO worldcupDTO = WorldcupDTO.builder()
                .worldcupNum(worldcup.getWorldcupNum())
                .description(worldcup.getDescription())
                .disclosure(worldcup.getDisclosure())
                .modDate(worldcup.getModDate())
                .regDate(worldcup.getRegDate())
                .title(worldcup.getTitle())
                .choice(choices)
                .build();

        return worldcupDTO;

    }

}
