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

    @ManyToOne
    private Choice choice;

    private int round;

    private int win;
    private int lose;
    private int first;

    private int next;

    @ManyToOne(fetch = FetchType.LAZY)
    private Worldcup worldcup;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

}
