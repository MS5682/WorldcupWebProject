package com.world.cup.dto;

import lombok.*;

import java.sql.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChoiceDTO {
    private int worldcupNum;
    private int choiceNum;
    private String name;
    private Byte type;
    private String link;
    private Integer first;
}
