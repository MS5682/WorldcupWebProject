package com.world.cup.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Choice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int choiceNum;

    private String name;
    private Byte type;
    private int first;

    private String path;
    private String uuid;
    private String imgName;

    @ManyToOne(fetch = FetchType.LAZY)
    private Worldcup worldcup;
}
