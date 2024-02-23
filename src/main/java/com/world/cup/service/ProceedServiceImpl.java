package com.world.cup.service;

import com.world.cup.dto.SaveDTO;
import com.world.cup.entity.Proceed;
import com.world.cup.entity.Worldcup;
import com.world.cup.repository.ProceedRepository;
import lombok.Builder;
import org.springframework.stereotype.Service;

@Service
@Builder
public class ProceedServiceImpl implements ProceedService {
    private ProceedRepository repository;

    @Override
    public void save(SaveDTO saveDTO) {
        System.out.println("월드컵 넘버 확인");
        System.out.println(saveDTO.getWorldNum());

        for (int i=0; i<saveDTO.getWinner().size(); i++) {
            Proceed p = Proceed.builder()
                    .round(saveDTO.getRound())
                    .user(null)
                    .worldcup(Worldcup.builder().worldcupNum(saveDTO.getWorldNum()).build())
                    .choice(convertEntity(saveDTO.getWinner().get(i)))
                    .next(1)
                    .build();
            System.out.println("Proceed 확인----------------------------------");
            System.out.println(p);
            repository.save(p);
        }
    }

    @Override
    public void autosave(SaveDTO saveDTO) {
        Proceed p = Proceed.builder()
                .proceedNum(repository.findByUserAndChoice(saveDTO.getUser(), convertEntity(saveDTO.getWinner().get(0))).getProceedNum())
                .round(saveDTO.getRound())
                .win(saveDTO.getWinner().get(0).getWin())
                .lose(saveDTO.getWinner().get(0).getLose())
                .worldcup(Worldcup.builder().worldcupNum(saveDTO.getWorldNum()).build())
                .choice(convertEntity(saveDTO.getWinner().get(0)))
                .next(1)
                .build();

        System.out.println("이긴 거---------------------------");
        System.out.println(p);
        repository.save(p);

        p = Proceed.builder()
                .proceedNum(repository.findByUserAndChoice(saveDTO.getUser(), convertEntity(saveDTO.getLoser().get(0))).getProceedNum())
                .round(saveDTO.getRound())
                .win(saveDTO.getLoser().get(0).getWin())
                .lose(saveDTO.getLoser().get(0).getLose())
                .worldcup(Worldcup.builder().worldcupNum(saveDTO.getWorldNum()).build())
                .choice(convertEntity(saveDTO.getLoser().get(0)))
                .next(0)
                .build();

        System.out.println("진 거-----------------------");
        System.out.println(p);

        repository.save(p);
    }


}
