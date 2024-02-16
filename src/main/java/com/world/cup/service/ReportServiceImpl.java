package com.world.cup.service;

import com.world.cup.dto.PageRequestDTO;
import com.world.cup.dto.PageResultDTO;
import com.world.cup.dto.ReportDTO;
import com.world.cup.dto.UserDTO;
import com.world.cup.entity.Report;
import com.world.cup.repository.ReportRepository;
import com.world.cup.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.function.Function;

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

    @Override
    public PageResultDTO<ReportDTO, Object[]> getReportList(PageRequestDTO requestDTO) {
        Sort sort = null;
        sort = Sort.by("regDate").descending();
        Page<Object[]> result = reportRepository.getReportListPage
                (requestDTO.getType(),
                        requestDTO.getKeyword(),
                        requestDTO.getPageable(sort));

        Function<Object[], ReportDTO> fn = (arr -> entityToDto((Report) arr[0],(String)arr[1],(String)arr[2]));

        return new PageResultDTO<>(result, fn);
    }

    @Override
    public ReportDTO getReportContent(String reportId) {
        Report report = reportRepository.getReportByReportId(reportId);

        return entityToDto(report,report.getWorldCup().getWorldcupNum(),report.getWorldCup().getTitle(),report.getUser().getId());
    }
}
