package com.world.cup.service;

import com.world.cup.dto.PageRequestDTO;
import com.world.cup.dto.PageResultDTO;
import com.world.cup.dto.ReportDTO;
import com.world.cup.dto.UserDTO;
import com.world.cup.entity.Report;
import com.world.cup.entity.User;
import com.world.cup.entity.Worldcup;

public interface ReportService {
    //신고하기
    int report(ReportDTO reportDTO);

    //reportList가져오기
    PageResultDTO<ReportDTO, Object[]> getReportList(PageRequestDTO pageRequestDTO);

    //report 상세 가져오기
    ReportDTO getReportContent(String reportId);
    default Report dtoToEntity(ReportDTO reportDTO){
        Worldcup worldcup = Worldcup.builder()
                .worldcupNum(reportDTO.getWorldcupNum())
                .title(reportDTO.getWorldcupTitle())
                .build();
        User user = User.builder()
                .id(reportDTO.getReporter())
                .build();
        Report report = Report.builder()
                .reportId(reportDTO.getReportId())
                .reportContent(reportDTO.getReportContent())
                .regDate(reportDTO.getRegDate())
                .worldCup(worldcup)
                .user(user)
                .build();

        return report;
    }




    default ReportDTO entityToDto(Report report, String worldcupTitle, String reporter) {
        return entityToDto(report, null, worldcupTitle, reporter);
    }

    default ReportDTO entityToDto(Report report, Integer worldcupNum, String worldcupTitle, String reporter) {
        ReportDTO reportDTO = ReportDTO.builder()
                .reportId(report.getReportId())
                .regDate(report.getRegDate())
                .reportContent(report.getReportContent())
                .worldcupTitle(worldcupTitle)
                .reporter(reporter)
                .build();

        if (worldcupNum != null) {
            reportDTO.setWorldcupNum(worldcupNum);
        }

        return reportDTO;
    }
}
