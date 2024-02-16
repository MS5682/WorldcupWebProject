package com.world.cup.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.world.cup.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.stream.Collectors;

public class ReportListRepositoryImpl extends QuerydslRepositorySupport implements ReportListRepository {

    public ReportListRepositoryImpl() {
        super(Report.class);
    }
    @Override
    public Page<Object[]> getReportListPage(String type, String keyword, Pageable pageable) {
        QReport report = QReport.report;
        QWorldcup worldcup = QWorldcup.worldcup;
        QUser user = QUser.user;

        JPQLQuery<Tuple> tuple = from(report)
                .leftJoin(report.worldCup,worldcup)
                .leftJoin(report.user,user)
                .select(report,worldcup.title,user.id)
                .groupBy(report.reportId,report.reportContent,report.regDate);


        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (type != null) {
            String[] typeArr = type.split("");
            BooleanBuilder coditionBuilder = new BooleanBuilder();
            for(String t : typeArr){
                switch (t){
                    case "t":
                        coditionBuilder.or(report.worldCup.title.contains(keyword));
                        break;
                    case "i":
                        coditionBuilder.or(report.user.id.contains(keyword));
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
            PathBuilder orderByExpression = new PathBuilder(Report.class, "report");
            tuple.orderBy(new OrderSpecifier(direction, orderByExpression.get(prop)));
        });



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
