package com.world.cup;

import com.world.cup.entity.Worldcup;

public interface Choiceinterface {
    int getChoiceNum();

    String getName();
    Byte getType();
    int getFirst();

    String getPath();
    String getUuid();
    String getImgName();

    int getWin();
    int getLose();
//
//    Worldcup getWorldCup();
}
