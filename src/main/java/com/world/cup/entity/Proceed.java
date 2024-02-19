package com.world.cup.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Proceed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int proceedNum;

    private int round;

    private int curRound;
    private int curSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    private Worldcup worldcup;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

}
