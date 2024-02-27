package com.world.cup.service;

import com.world.cup.dto.SaveDTO;
import com.world.cup.entity.Choice;
import com.world.cup.entity.Proceed;
import com.world.cup.entity.User;
import com.world.cup.entity.Worldcup;
import com.world.cup.repository.PlayingRepository;
import com.world.cup.repository.ProceedRepository;
import lombok.Builder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Builder
public class ProceedServiceImpl implements ProceedService {
    private ProceedRepository repository;
    private PlayingRepository playingRepository;

    @Override
    public void save(SaveDTO saveDTO) {
        System.out.println("월드컵 넘버 확인");
        System.out.println(saveDTO.getWorldNum());

        for (int i=0; i<saveDTO.getWinner().size(); i++) {
            Proceed p = Proceed.builder()
                    .roundNext(saveDTO.getRoundNext())
                    .user(User.builder().id("test1234").build())
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
                .proceedNum(repository.findByUser_IdAndChoice(saveDTO.getUserId(), convertEntity(saveDTO.getWinner().get(0))).getProceedNum())
                .roundNext(saveDTO.getRoundNext())
                .win(saveDTO.getWinner().get(0).getWin())
                .lose(saveDTO.getWinner().get(0).getLose())
                .first(saveDTO.getWinner().get(0).getFirst())
                .user(User.builder().id(saveDTO.getUserId()).build())
                .worldcup(Worldcup.builder().worldcupNum(saveDTO.getWorldNum()).build())
                .choice(convertEntity(saveDTO.getWinner().get(0)))
                .next(1)
                .build();

        System.out.println("이긴 거---------------------------");
        System.out.println(p);
        repository.save(p);

        p = Proceed.builder()
                .proceedNum(repository.findByUser_IdAndChoice(saveDTO.getUserId(), convertEntity(saveDTO.getLoser().get(0))).getProceedNum())
                .roundNext(saveDTO.getRoundNext())
                .win(saveDTO.getLoser().get(0).getWin())
                .lose(saveDTO.getLoser().get(0).getLose())
                .first(saveDTO.getLoser().get(0).getFirst())
                .user(User.builder().id(saveDTO.getUserId()).build())
                .worldcup(Worldcup.builder().worldcupNum(saveDTO.getWorldNum()).build())
                .choice(convertEntity(saveDTO.getLoser().get(0)))
                .next(0)
                .build();

        System.out.println("진 거-----------------------");
        System.out.println(p);

        repository.save(p);
    }

    @Override
    public void finalsave(SaveDTO saveDTO) {
        List<Proceed> resultAll = repository.endPlayResult(saveDTO.getUserId(), saveDTO.getWorldNum());

        for (Proceed p : resultAll) {

            Choice c = Choice.builder()
                    .choiceNum(p.getChoice().getChoiceNum())
                    .name(p.getChoice().getName())
                    .type(p.getChoice().getType())
                    .worldcup(p.getWorldcup())
                    .imgName(p.getChoice().getImgName())
                    .path(p.getChoice().getPath())
                    .uuid(p.getChoice().getUuid())
                    .win(p.getWin())
                    .lose(p.getLose())
                    .first(p.getFirst())
                    .build();
            System.out.println(c);
            playingRepository.save(c);

            repository.delete(p);
        }
    }

    @Override
    public List<Choice> savefileload(String userId, int worldcupNum) {

        List<Proceed> proceedList = repository.findProceedsByUserIdAndWorldcup_WorldcupNum(userId, worldcupNum);

        System.out.println("proceedList 확인---------------------------");
        System.out.println(proceedList);

        List<Choice> choiceList = new ArrayList<>();

        for (Proceed p : proceedList) {
            if (p.getNext() == 1) {
                Choice c = Choice.builder()
                        .choiceNum(p.getChoice().getChoiceNum())
                        .name(p.getChoice().getName())
                        .type(p.getChoice().getType())
                        .imgName(p.getChoice().getImgName())
                        .path(p.getChoice().getPath())
                        .uuid(p.getChoice().getUuid())
                        .win(p.getWin())
                        .lose(p.getLose())
                        .build();
                choiceList.add(c);
            }

        }

        System.out.println("choiceList 확인");
        System.out.println(choiceList);

        return choiceList;
    }

    @Override
    public int[] round(String userId, int worldcupNum) {
        List<Proceed> proceedList = repository.findProceedsByUserIdAndWorldcup_WorldcupNum(userId, worldcupNum);

        int[] nextArr = new int[proceedList.size()];

        for (int i=0;i<proceedList.size();i++) {
            nextArr[i] = proceedList.get(i).getRoundNext();
        }
        return nextArr;
    }

    @Override
    public boolean havesave(String userId, int worldcupNum) {
        List<Proceed> checksave = repository.endPlayResult(userId, worldcupNum);

        System.out.println("확인용");
        System.out.println(checksave);
        System.out.println(checksave.isEmpty());
        if (checksave.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }


}
