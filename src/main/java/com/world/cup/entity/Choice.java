package com.world.cup.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
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

    private int win;
    private int lose;

    @ManyToOne(fetch = FetchType.LAZY)
    private Worldcup worldcup;

    public void changeName(String name){
        this.name = name;
    }

    public void changePath(String path){
        this.path = path;
    }

    public void changeUuid(String uuid){
        this.uuid = uuid;
    }

    public void changeImgName(String imgName){
        this.imgName = imgName;
    }
}
