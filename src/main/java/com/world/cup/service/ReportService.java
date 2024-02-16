package com.world.cup.service;

import com.world.cup.dto.ReportDTO;
import com.world.cup.dto.UserDTO;
import com.world.cup.entity.Report;
import com.world.cup.entity.User;
import com.world.cup.entity.Worldcup;

public interface ReportService {
    int report(ReportDTO reportDTO);

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

    default ReportDTO entityToDto(Report report){
        ReportDTO reportDTO = ReportDTO.builder().build();

        return reportDTO;
    }
}
