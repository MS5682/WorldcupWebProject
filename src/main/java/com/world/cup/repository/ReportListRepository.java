package com.world.cup.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReportListRepository {
    Page<Object[]> getReportListPage(String type, String keyword, Pageable pageable);
}
