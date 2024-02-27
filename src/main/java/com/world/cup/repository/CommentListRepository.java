package com.world.cup.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentListRepository {
    Page<Object[]> getCommentList(int worldcupNum, int commentType, Pageable pageable);

}
