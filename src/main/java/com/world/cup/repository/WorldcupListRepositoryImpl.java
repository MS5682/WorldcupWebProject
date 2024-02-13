package com.world.cup.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.world.cup.entity.QChoice;
import com.world.cup.entity.QWorldcup;
import com.world.cup.entity.Worldcup;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.stream.Collectors;
@Log4j2
public class WorldcupListRepositoryImpl extends QuerydslRepositorySupport implements WorldcupListRepository {

    public WorldcupListRepositoryImpl() {super(Worldcup.class);}

    @Override
    public Page<Object[]> getWorldcupList(String type, String keyword, Pageable pageable) {
        QWorldcup worldcup = QWorldcup.worldcup;
        QChoice choice1 = new QChoice("choice1");
        QChoice choice2 = new QChoice("choice2");

        JPQLQuery<Worldcup> jpqlQuery = from(worldcup);
        jpqlQuery.leftJoin(choice1).on(worldcup.eq(choice1.worldcup));
        jpqlQuery.leftJoin(choice2).on(worldcup.eq(choice2.worldcup).and(choice1.choiceNum.gt(choice2.choiceNum)));
        JPQLQuery<Tuple> tuple = jpqlQuery.select(worldcup, choice1.name, choice2.name,
                choice1.type, choice2.type, choice1.uuid, choice2.uuid,
                choice1.imgName, choice2.imgName, choice1.path, choice2.path);

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (type != null) {
            String[] typeArr = type.split("");
            BooleanBuilder coditionBuilder = new BooleanBuilder();
            for(String t : typeArr){
                switch (t){
                    case "t":
                        coditionBuilder.or(worldcup.title.contains(keyword));
                        break;
                    case "m":
                        coditionBuilder.or(choice1.name.contains(keyword)
                                .or(choice2.name.contains(keyword)));
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
            PathBuilder orderByExpression = new PathBuilder(Worldcup.class, "worldcup");
            tuple.orderBy(new OrderSpecifier(direction, orderByExpression.get(prop)));
        });


        tuple.groupBy(worldcup.worldcupNum);
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
