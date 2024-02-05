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
    private String link;
    private int first;

    @ManyToOne(fetch = FetchType.LAZY)
    private Worldcup worldcup;
}
