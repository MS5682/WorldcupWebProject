package com.world.cup.service;

import com.world.cup.Choiceinterface;
import com.world.cup.entity.Worldcup;

import java.util.List;

public interface PlayingService {
    List<Choiceinterface> selectCandi(int worldCupID);

    Worldcup worldCupTitle(int worldCupID);
}
