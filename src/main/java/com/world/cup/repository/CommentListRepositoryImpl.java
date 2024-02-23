package com.world.cup.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.world.cup.entity.Choice;
import com.world.cup.entity.Comment;
import com.world.cup.entity.QChoice;
import com.world.cup.entity.QComment;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class CommentListRepositoryImpl extends QuerydslRepositorySupport implements CommentListRepository {

    public CommentListRepositoryImpl() {super(Comment.class);}


    @Override
    public Page<Object[]> getCommentList(Integer choiceNum, int worldcupNum, int commentType, Pageable pageable) {
        QComment comment = QComment.comment;
        JPQLQuery<Comment> jpqlQuery = from(comment);
        jpqlQuery.select(comment);

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(comment.worldcup.worldcupNum.eq(worldcupNum));
        booleanBuilder.and(comment.type.eq(commentType));
        if (choiceNum != null) {
            BooleanBuilder conditionBuilder = new BooleanBuilder().or(comment.choice.choiceNum.eq(choiceNum));
            booleanBuilder.and(conditionBuilder);
        }

        Sort sort = pageable.getSort();
        sort.stream().forEach(order -> {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String prop = order.getProperty();
            PathBuilder orderByExpression = new PathBuilder(Comment.class, "comment");
            jpqlQuery.orderBy(new OrderSpecifier(direction, orderByExpression.get(prop)));
        });

        jpqlQuery.offset(pageable.getOffset());
        jpqlQuery.limit(pageable.getPageSize());

        List<Comment> result = jpqlQuery.where(booleanBuilder).fetch();
        long count = jpqlQuery.fetchCount();
        return new PageImpl<Object[]>(
                result.stream().map(t -> new Object[]{t}).collect(Collectors.toList()),
                pageable,
                count
        );
    }
}