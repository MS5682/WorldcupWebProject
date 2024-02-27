package com.world.cup.service;

import com.world.cup.dto.*;
import com.world.cup.entity.Choice;
import com.world.cup.entity.Comment;
import com.world.cup.entity.User;
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
        Function<Object[], CommentDTO> fn = ((en) -> entityToDto((Comment) en[0], (User) en[1]));
        Sort sort = Sort.by("regDate").descending();
        Pageable pageable = pageRequestDTO.getCommentPageable(sort);
        if(pageRequestDTO.getCommentType() == 1){
            pageable = pageRequestDTO.getRequestPageable(sort);
        }
        Page<Object[]> result = commentRepository.getCommentList(
                pageRequestDTO.getWorldcupNum(),
                pageRequestDTO.getCommentType(),
                pageable
        );
        return new PageResultDTO<>(result,fn);
    }

    @Override
    public void addComment(CommentDTO commentDTO) {
        Comment comment = dtoToEntity(commentDTO);
        commentRepository.save(comment);
    }

    @Override
    @Transactional
    public void modifyComment(CommentDTO commentDTO) {
        Comment comment = commentRepository.getOne(commentDTO.getCommentNum());
        if(comment != null){
            comment.changeContent(commentDTO.getContent());
        }
        commentRepository.save(comment);
    }

    @Override
    public void deleteComment(CommentDTO commentDTO) {
        commentRepository.deleteById(commentDTO.getCommentNum());
    }
}
