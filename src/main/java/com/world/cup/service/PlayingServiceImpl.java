package com.world.cup.service;

import com.world.cup.Choiceinterface;
import com.world.cup.entity.Worldcup;
import com.world.cup.repository.PlayingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayingServiceImpl implements PlayingService {
    private final PlayingRepository playingRepository;

    @Override
    public List<Choiceinterface> selectCandi(int worldCupID) {
        List<Choiceinterface> candi = playingRepository.selectCandi(worldCupID);

        return candi;
    }

    @Override
    public Worldcup worldCupTitle(int worldCupID) {
        Worldcup title = playingRepository.worldCupTitle(worldCupID);

        return title;
    }
}
