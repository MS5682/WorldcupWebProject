package com.world.cup.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.JPQLQueryFactory;
import com.world.cup.entity.QChoice;
import com.world.cup.entity.QProceed;
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
    public Page<Object[]> getWorldcupList(String type, String keyword, Pageable pageable, String userId, Byte disclosure) {
        QWorldcup worldcup = QWorldcup.worldcup;
        QChoice choice1 = new QChoice("choice1");
        QChoice choice2 = new QChoice("choice2");

        JPQLQuery<Worldcup> jpqlQuery = from(worldcup);
        jpqlQuery.leftJoin(choice1).on(worldcup.eq(choice1.worldcup).and(
                choice1.choiceNum.eq(
                        JPAExpressions.select(choice1.choiceNum.min())
                                .from(choice1)
                                .where(choice1.worldcup.worldcupNum.eq(worldcup.worldcupNum))
                )
        ));
        jpqlQuery.leftJoin(choice2).on(worldcup.eq(choice2.worldcup).and(choice1.choiceNum.lt(choice2.choiceNum)));
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
        if(userId != null){
            booleanBuilder.and(worldcup.user.id.eq(userId));
        }
        if(disclosure == null){
        }
        else if(disclosure == 1) {
            BooleanExpression disclosureCondition = worldcup.disclosure.eq((byte) 1);
            booleanBuilder.and(disclosureCondition);
        }else if(disclosure == 0){
            BooleanExpression disclosureCondition = worldcup.disclosure.eq((byte) 0);
            booleanBuilder.and(disclosureCondition);
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

    @Override
    public Page<Object[]> getProceedWorldcupList(Pageable pageable, String userId) {
        QWorldcup worldcup = QWorldcup.worldcup;
        QChoice choice1 = new QChoice("choice1");
        QChoice choice2 = new QChoice("choice2");
        QProceed proceed = QProceed.proceed;

        JPQLQuery<Worldcup> jpqlQuery = from(worldcup);
        jpqlQuery.leftJoin(choice1).on(worldcup.eq(choice1.worldcup).and(
                choice1.choiceNum.eq(
                        JPAExpressions.select(choice1.choiceNum.min())
                                .from(choice1)
                                .where(choice1.worldcup.worldcupNum.eq(worldcup.worldcupNum))
                )
        ));
        jpqlQuery.leftJoin(choice2).on(worldcup.eq(choice2.worldcup).and(choice1.choiceNum.lt(choice2.choiceNum)));
        JPQLQuery<Tuple> tuple = jpqlQuery.select(worldcup, choice1.name, choice2.name,
                choice1.type, choice2.type, choice1.uuid, choice2.uuid,
                choice1.imgName, choice2.imgName, choice1.path, choice2.path);

        BooleanBuilder booleanBuilder = new BooleanBuilder();


        booleanBuilder.and(worldcup.worldcupNum.in(
                JPAExpressions.selectDistinct(proceed.worldcup.worldcupNum)
                        .from(proceed)
                        .where(proceed.user.id.eq(userId))
        ));

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


    @Override
    public Page<Object[]> getPublicWorldcupList(String type, String keyword, Pageable pageable) {
        // 엔티티와 조인에 사용할 Q 클래스 생성
        QWorldcup worldcup = QWorldcup.worldcup;
        QChoice choice1 = new QChoice("choice1");
        QChoice choice2 = new QChoice("choice2");

        // JPQL 쿼리 생성
        JPQLQuery<Worldcup> jpqlQuery = from(worldcup);
        jpqlQuery.leftJoin(choice1).on(worldcup.eq(choice1.worldcup).and(
                choice1.choiceNum.eq(
                        JPAExpressions.select(choice1.choiceNum.min())
                                .from(choice1)
                                .where(choice1.worldcup.worldcupNum.eq(worldcup.worldcupNum))
                )
        ));

        jpqlQuery.leftJoin(choice2).on(worldcup.eq(choice2.worldcup).and(choice1.choiceNum.lt(choice2.choiceNum)));
        JPQLQuery<Tuple> tuple = jpqlQuery.select(worldcup, choice1.name, choice2.name,
                choice1.type, choice2.type, choice1.uuid, choice2.uuid,
                choice1.imgName, choice2.imgName, choice1.path, choice2.path);

        // 조건 생성 및 적용
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(worldcup.disclosure.eq((byte) 1)); // 공개된 월드컵만 조회
        if (type != null) {
            String[] typeArr = type.split("");
            BooleanBuilder conditionBuilder = new BooleanBuilder();
            for(String t : typeArr){
                switch (t){
                    case "t":
                        conditionBuilder.or(worldcup.title.contains(keyword)); // 제목에 대한 검색 조건
                        break;
                    case "m":
                        conditionBuilder.or(choice1.name.contains(keyword)
                                .or(choice2.name.contains(keyword))); // 선택지에 대한 검색 조건
                        break;
                }
            }
            booleanBuilder.and(conditionBuilder); // 검색 조건 추가
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

        // 그룹화, 페이징 처리, 결과 조회
        tuple.groupBy(worldcup.worldcupNum);
        tuple.offset(pageable.getOffset());
        tuple.limit(pageable.getPageSize());
        List<Tuple> result = tuple.fetch();
        long count = tuple.fetchCount();

        // 페이지 객체 생성하여 반환
        return new PageImpl<>(
                result.stream().map(Tuple::toArray).collect(Collectors.toList()), // 결과 매핑
                pageable, // 페이지 정보
                count // 전체 개수
        );
    }

    @Override
    public Page<Object[]> getPrivateWorldcupList(String type, String keyword, Pageable pageable) {
        // 엔티티와 조인에 사용할 Q 클래스 생성
        QWorldcup worldcup = QWorldcup.worldcup;
        QChoice choice1 = new QChoice("choice1");
        QChoice choice2 = new QChoice("choice2");

        // JPQL 쿼리 생성
        JPQLQuery<Worldcup> jpqlQuery = from(worldcup);
        jpqlQuery.leftJoin(choice1).on(worldcup.eq(choice1.worldcup).and(
                choice1.choiceNum.eq(
                        JPAExpressions.select(choice1.choiceNum.min())
                                .from(choice1)
                                .where(choice1.worldcup.worldcupNum.eq(worldcup.worldcupNum))
                )
        ));
        jpqlQuery.leftJoin(choice2).on(worldcup.eq(choice2.worldcup).and(choice1.choiceNum.lt(choice2.choiceNum)));
        JPQLQuery<Tuple> tuple = jpqlQuery.select(worldcup, choice1.name, choice2.name,
                choice1.type, choice2.type, choice1.uuid, choice2.uuid,
                choice1.imgName, choice2.imgName, choice1.path, choice2.path);

        // 조건 생성 및 적용
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(worldcup.disclosure.eq((byte) 0)); // 공개된 월드컵만 조회(여기만 변경)
        if (type != null) {
            String[] typeArr = type.split("");
            BooleanBuilder conditionBuilder = new BooleanBuilder();
            for(String t : typeArr){
                switch (t){
                    case "t":
                        conditionBuilder.or(worldcup.title.contains(keyword)); // 제목에 대한 검색 조건
                        break;
                    case "m":
                        conditionBuilder.or(choice1.name.contains(keyword)
                                .or(choice2.name.contains(keyword))); // 선택지에 대한 검색 조건
                        break;
                }
            }
            booleanBuilder.and(conditionBuilder); // 검색 조건 추가
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

        // 그룹화, 페이징 처리, 결과 조회
        tuple.groupBy(worldcup.worldcupNum);
        tuple.offset(pageable.getOffset());
        tuple.limit(pageable.getPageSize());
        List<Tuple> result = tuple.fetch();
        long count = tuple.fetchCount();

        // 페이지 객체 생성하여 반환
        return new PageImpl<>(
                result.stream().map(Tuple::toArray).collect(Collectors.toList()), // 결과 매핑
                pageable, // 페이지 정보
                count // 전체 개수
        );
    }
}
