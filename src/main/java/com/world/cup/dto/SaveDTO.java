package com.world.cup.dto;

import com.world.cup.entity.Choice;
import com.world.cup.entity.User;
import com.world.cup.entity.Worldcup;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@RequiredArgsConstructor
@ToString
public class SaveDTO {
    private int proceedNum;
    private int round;
    private int win;
    private int lose;
    private int first;
    private User user;
    private Worldcup worldcup;
    private Choice choice;

    private int next;
    private String userId;

    private List<ChoiceDTO> winner;
    private List<ChoiceDTO> loser;
    private int worldNum;
}
