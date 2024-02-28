package com.world.cup.service;

import com.world.cup.dto.SaveDTO;
import com.world.cup.entity.Choice;
import com.world.cup.entity.Proceed;
import com.world.cup.entity.User;
import com.world.cup.entity.Worldcup;
import com.world.cup.repository.PlayingRepository;
import com.world.cup.repository.ProceedRepository;
import com.world.cup.repository.WorldcupRepository;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Builder
public class ProceedServiceImpl implements ProceedService {
    private ProceedRepository repository;
    private PlayingRepository playingRepository;
    private WorldcupRepository worldcupRepository;

    @Override
    public void save(SaveDTO saveDTO) {
        for (int i=0; i<saveDTO.getWinner().size(); i++) {
            Proceed p = Proceed.builder()
                    .roundNext(saveDTO.getRoundNext())
                    .user(User.builder().id(saveDTO.getUserId()).build())
                    .worldcup(Worldcup.builder().worldcupNum(saveDTO.getWorldNum()).build())
                    .choice(convertEntity(saveDTO.getWinner().get(i)))
                    .next(1)
                    .build();
            System.out.println("ppppppp"+p);
            repository.save(p);
        }
    }

    @Override
    public void autosave(SaveDTO saveDTO) {
//        System.out.println("save : " + convertEntity(saveDTO.getWinner().get(0)));
        System.out.println("save id : " + saveDTO.getUserId());
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

            playingRepository.save(c);

            repository.delete(p);
        }
    }

    @Override
    public void nologinsave(SaveDTO saveDTO) {
        for (int i=0; i<saveDTO.getWinner().size(); i++) {
            Choice c = convertDTO(saveDTO.getWinner().get(i), worldcupRepository, saveDTO.getWorldNum());
            playingRepository.save(c);
        }

        for (int i=0; i<saveDTO.getLoser().size(); i++) {
            Choice c = convertDTO(saveDTO.getLoser().get(i), worldcupRepository, saveDTO.getWorldNum());
            playingRepository.save(c);
        }
    }

    @Override
    public List<Choice> savefileload(String userId, int worldcupNum) {

        List<Proceed> proceedList = repository.findProceedsByUserIdAndWorldcup_WorldcupNum(userId, worldcupNum);

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

        return choiceList;
    }

    @Override
    public int[] round(String userId, int worldcupNum) {
        List<Proceed> proceedList = repository.findProceedsByUserIdAndWorldcup_WorldcupNum(userId, worldcupNum);
        int i = 0;

        int[] nextArr = new int[proceedList.size()];

        for (Proceed p : proceedList) {
            if (p.getNext() == 1) {
                nextArr[i] = p.getRoundNext();
                i++;
            }
        }

        return nextArr;
    }

    @Override
    public void savedelete(String userId, int worldcupNum) {
        List<Proceed> proceedList = repository.endPlayResult(userId, worldcupNum);

        repository.deleteAll(proceedList);
    }

    @Override
    public boolean havesave(String userId, int worldcupNum) {
        List<Proceed> checksave = repository.endPlayResult(userId, worldcupNum);

        return !checksave.isEmpty();
    }


}
