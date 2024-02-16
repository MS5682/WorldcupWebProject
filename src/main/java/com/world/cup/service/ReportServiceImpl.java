package com.world.cup.service;

import com.world.cup.dto.ReportDTO;
import com.world.cup.entity.Report;
import com.world.cup.entity.User;
import com.world.cup.repository.ReportRepository;
import com.world.cup.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;

    private final UserRepository userRepository;
    @Override
    public int report(ReportDTO reportDTO) {

        Report report = dtoToEntity(reportDTO);
        log.info(report);
        reportRepository.save(report);

        return report.getReportId();
    }
}
