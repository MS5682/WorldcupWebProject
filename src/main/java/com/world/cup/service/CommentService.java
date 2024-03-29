package com.world.cup.service;

import com.world.cup.dto.*;
import com.world.cup.entity.Choice;
import com.world.cup.entity.Comment;
import com.world.cup.entity.User;
import com.world.cup.entity.Worldcup;


public interface CommentService {
    PageResultDTO<CommentDTO, Object[]> getCommentPage(PageRequestDTO pageRequestDTO);
    void addComment(CommentDTO commentDTO);
    void modifyComment(CommentDTO commentDTO);
    void deleteComment(CommentDTO commentDTO);
    default Comment dtoToEntity(CommentDTO commentDTO){
        Worldcup worldcup = Worldcup.builder()
                .worldcupNum(commentDTO.getWorldcupNum())
                .build();
        Choice choice = null;
        if(commentDTO.getChoiceNum() != null) {
            choice = Choice.builder()
                    .choiceNum(commentDTO.getChoiceNum())
                    .build();
        }
        User user = User.builder()
                .id(commentDTO.getId())
                .build();
        Comment comment = Comment.builder()
                .commentNum(commentDTO.getCommentNum())
                .regDate(commentDTO.getRegDate())
                .choice(choice)
                .content(commentDTO.getContent())
                .type(commentDTO.getType())
                .user(user)
                .worldcup(worldcup)
                .build();
        return comment;
    }

    default CommentDTO entityToDto(Comment comment, User user){
        CommentDTO commentDTO = CommentDTO.builder()
                .content(comment.getContent())
                .commentNum(comment.getCommentNum())
                .regDate(comment.getRegDate())
                .worldcupNum(comment.getWorldcup().getWorldcupNum())
                .type(comment.getType())
                .id(user.getId())
                .password(user.getPassword())
                .email(user.getEmail())
                .build();
        if(comment.getChoice() != null){
            commentDTO.setChoiceNum(comment.getChoice().getChoiceNum());
            commentDTO.setChoiceName(comment.getChoice().getName());
        }
        return commentDTO;
    }
}
