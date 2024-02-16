package com.world.cup.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.world.cup.entity.Choice;
import com.world.cup.entity.QChoice;
import com.world.cup.entity.QWorldcup;
import com.world.cup.entity.Worldcup;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class ChoiceListRepositoryImpl extends QuerydslRepositorySupport implements ChoiceListRepository {

    public ChoiceListRepositoryImpl() {super(Choice.class);}
    @Override
    public Page<Object[]> getChoiceList(String type, String keyword, int worldcupNum, Pageable pageable) {
        QChoice choice = QChoice.choice;
        JPQLQuery<Choice> jpqlQuery = from(choice);

        jpqlQuery.select(choice);

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(choice.worldcup.worldcupNum.eq(worldcupNum));
        if (type != null) {
            String[] typeArr = type.split("");
            BooleanBuilder conditionBuilder = new BooleanBuilder(); // 변수명 수정
            for(String t : typeArr){
                switch (t){
                    case "n":
                        conditionBuilder.or(choice.name.contains(keyword));
                        break;
                }
            }
            booleanBuilder.and(conditionBuilder);
        }

        // 정렬 추가
        Sort sort = pageable.getSort();
        sort.stream().forEach(order -> {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String prop = order.getProperty();

            // 정렬 기준값 정의
            PathBuilder orderByExpression = new PathBuilder(Choice.class, "choice");
            jpqlQuery.orderBy(new OrderSpecifier(direction, orderByExpression.get(prop)));
        });

        jpqlQuery.offset(pageable.getOffset());
        jpqlQuery.limit(pageable.getPageSize());

        // 결과 조회
        List<Choice> result = jpqlQuery.where(booleanBuilder).fetch(); // where 조건 추가 및 fetch 호출 수정
        long count = jpqlQuery.fetchCount(); // count 호출 수정

        return new PageImpl<Object[]>(
                result.stream().map(t -> new Object[]{t}).collect(Collectors.toList()), // Choice 엔티티를 배열로 매핑
                pageable,
                count
        );
    }

}
