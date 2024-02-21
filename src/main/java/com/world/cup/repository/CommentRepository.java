package com.world.cup.repository;

import com.world.cup.entity.Choice;
import com.world.cup.entity.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer>, CommentListRepository {
}
