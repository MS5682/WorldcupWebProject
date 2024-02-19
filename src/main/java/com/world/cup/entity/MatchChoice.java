package com.world.cup.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class MatchChoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int matchNum;

    private int curRound;

    private int result;

    @ManyToOne(fetch = FetchType.LAZY)
    private Proceed proceed;

    @ManyToOne(fetch = FetchType.LAZY)
    private Choice choice;

}
