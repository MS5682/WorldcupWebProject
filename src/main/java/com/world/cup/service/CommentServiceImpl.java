package com.world.cup.service;

import com.world.cup.dto.*;
import com.world.cup.entity.Choice;
import com.world.cup.entity.Comment;
import com.world.cup.repository.ChoiceRepository;
import com.world.cup.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public PageResultDTO<CommentDTO, Object[]> getCommentPage(PageRequestDTO pageRequestDTO) {
        Function<Object[], CommentDTO> fn = ((en) -> entityToDto((Comment) en[0]));
        Sort sort = Sort.by("regDate").descending();
        Pageable pageable = pageRequestDTO.getCommentPageable(sort);
        if(pageRequestDTO.getCommentType() == 1){
            pageable = pageRequestDTO.getRequestPageable(sort);
        }
        Page<Object[]> result = commentRepository.getCommentList(
                pageRequestDTO.getChoiceNum(),
                pageRequestDTO.getWorldcupNum(),
                pageRequestDTO.getCommentType(),
                pageable
        );
        return new PageResultDTO<>(result,fn);
    }

    @Override
    public void addChoice(CommentDTO commentDTO) {

    }

    @Override
    public void modifyChoiceName(CommentDTO commentDTO) {

    }

    @Override
    public void deleteChoice(CommentDTO commentDTO) {

    }


//    @Override
//    @Transactional
//    public WorldcupDTO getChoiceToWorldcup(WorldcupDTO worldcupDTO) {
//        int worldcupNum = worldcupDTO.getWorldcupNum();
//        List<Choice> choices = choiceRepository.getChoiceByWorldcupNum(worldcupNum, null);
//
//        List<ChoiceDTO> choiceDTOs = choices.stream()
//                .map(this::entityToDto)
//                .collect(Collectors.toList());
//
//        worldcupDTO.setChoice(choiceDTOs);
//
//        return worldcupDTO;
//    }
//    @Override
//    public WorldcupDTO getTopTen(WorldcupDTO worldcupDTO) {
//        int worldcupNum = worldcupDTO.getWorldcupNum();
//        Pageable pageable = PageRequest.of(0, 10);
//        List<Choice> choices = choiceRepository.getChoiceByWorldcupNum(worldcupNum, pageable);
//
//        List<ChoiceDTO> choiceDTOs = choices.stream()
//                .map(this::entityToDto)
//                .collect(Collectors.toList());
//
//        worldcupDTO.setChoice(choiceDTOs);
//
//        return worldcupDTO;
//    }
//    @Override
//    public WorldcupDTO getChoiceRank(WorldcupDTO worldcupDTO) {
//        int worldcupNum = worldcupDTO.getWorldcupNum();
//        Pageable pageable = PageRequest.of(0, 3);
//        List<Choice> choices = choiceRepository.getChoiceByWorldcupNum(worldcupNum, pageable);
//
//        List<ChoiceDTO> choiceDTOs = choices.stream()
//                .map(this::entityToDto)
//                .collect(Collectors.toList());
//
//        worldcupDTO.setChoice(choiceDTOs);
//
//        return worldcupDTO;
//    }
//
//    @Override
//    public PageResultDTO<ChoiceDTO, Object[]> getChoicePage(PageRequestDTO pageRequestDTO) {
//        Function<Object[], ChoiceDTO> fn = ((en) -> entityToDto((Choice)en[0]));
//        pageRequestDTO.setSize(5);
//        Sort sort = null;
//        if (pageRequestDTO.getOrder() == 0) {
//            sort = Sort.by("choiceNum").descending();
//        } else if(pageRequestDTO.getOrder() == 1) {
//            sort = Sort.by("first").descending();
//        }
//        Pageable pageable = pageRequestDTO.getPageable(sort);
//        Page<Object[]> result = choiceRepository.getChoiceList(pageRequestDTO.getType(),
//                pageRequestDTO.getKeyword(),
//                pageRequestDTO.getWorldcupNum(),
//                pageable,
//                null);
//
//
//
//        return new PageResultDTO<>(result,fn);
//    }
//
//    @Override
//    public Integer sumFirst(WorldcupDTO worldcupDTO) {
//        return choiceRepository.sumFirstByWorldcupWorldcupNum(worldcupDTO.getWorldcupNum());
//
//    }
//
//    @Override
//    public void addChoice(ChoiceDTO choiceDTO) {
//        Choice choice = dtoToEntity(choiceDTO);
//        choiceRepository.save(choice);
//    }
//
//    @Override
//    @Transactional
//    public void modifyChoiceName(ChoiceDTO choiceDTO) {
//        Choice choice = choiceRepository.getOne(choiceDTO.getChoiceNum());
//        if(choice != null){
//            choice.changeName(choiceDTO.getName());
//        }
//        choiceRepository.save(choice);
//    }
//
//    @Override
//    @Transactional
//    public void modifyChoiceImg(ChoiceDTO choiceDTO) {
//        Choice choice = choiceRepository.getOne(choiceDTO.getChoiceNum());
//        if(choice != null){
//            choice.changeImgName(choiceDTO.getImgName());
//            choice.changePath(choiceDTO.getPath());
//            choice.changeUuid(choiceDTO.getUuid());
//        }
//        choiceRepository.save(choice);
//    }
//
//    @Override
//    @Transactional
//    public void modifyChoiceVideo(ChoiceDTO choiceDTO) {
//        Choice choice = choiceRepository.getOne(choiceDTO.getChoiceNum());
//        if(choice != null){
//            choice.changePath(choiceDTO.getPath());
//            choice.changeImgName(choiceDTO.getImgName());
//        }
//        log.info(choiceDTO);
//        log.info(choice);
//        choiceRepository.save(choice);
//    }
//
//    @Override
//    @Transactional
//    public void deleteChoice(ChoiceDTO choiceDTO) {
//        choiceRepository.deleteById(choiceDTO.getChoiceNum());
//    }


}
