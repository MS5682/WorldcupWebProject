package com.world.cup.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.world.cup.entity.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class UserListRepositoryImpl extends QuerydslRepositorySupport implements UserListRepository {

    public UserListRepositoryImpl() {
        super(User.class);
    }

    @Override
    public Page<Object[]> getMemberListPage(String type, String keyword, Pageable pageable) {
        QUser user = QUser.user;
        QWorldcup worldcup = QWorldcup.worldcup;

        JPQLQuery<User> jpqlQuery = from(user);
        jpqlQuery.leftJoin(worldcup).on(user.eq(worldcup.user));
        JPQLQuery<Tuple> tuple = jpqlQuery.select(user,worldcup.title);


        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (type != null) {
            String[] typeArr = type.split("");
            BooleanBuilder coditionBuilder = new BooleanBuilder();
            for(String t : typeArr){
                switch (t){
                    case "i":
                        coditionBuilder.or(user.id.contains(keyword));
                        break;
                    case "e":
                        coditionBuilder.or(user.email.contains(keyword));
                        break;

                }
            }
            booleanBuilder.and(coditionBuilder);
        }

        tuple.where(booleanBuilder);

        // 정렬 추가
        Sort sort = pageable.getSort();
        sort.stream().forEach(order -> {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String prop = order.getProperty();

            // 정렬 기준값 정의
            PathBuilder orderByExpression = new PathBuilder(User.class, "user");
            tuple.orderBy(new OrderSpecifier(direction, orderByExpression.get(prop)));
        });


        //tuple.groupBy(user.regDate);
        // 페이징 처리
        tuple.offset(pageable.getOffset());
        tuple.limit(pageable.getPageSize());

        // 결과 조회
        List<Tuple> result = tuple.fetch();
        long count = tuple.fetchCount();

        return new PageImpl<Object[]>(
                result.stream().map(t -> t.toArray()).collect(Collectors.toList()),
                pageable,
                count
        );
    }
}
