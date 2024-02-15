package com.world.cup.repository;

import com.world.cup.entity.Report;
import com.world.cup.entity.User;
import com.world.cup.entity.Worldcup;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Date;

@SpringBootTest
public class ReportRepositoryTests {

    @Autowired
    private ReportRepository reportRepository;

    @Test
    public void insertReport(){
        Worldcup worldcup =Worldcup.builder().worldcupNum(1).build();

        User user = User.builder().id("admin").build();

        LocalDateTime regDate = LocalDateTime.now();

        Report report = Report.builder()
                .reportId(1)
                .reportContent("신고하기 테스트")
                .regDate(regDate)
                .worldCup(worldcup)
                .user(user)
                .build();

        reportRepository.save(report);
    }
}
