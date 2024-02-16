package com.world.cup.repository;

import com.world.cup.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReportRepository extends JpaRepository<Report, Long>,ReportListRepository {
    @Query("select r,w.title from Report r join r.worldCup w where r.reportId = :reportId")
    Report getReportByReportId(@Param("reportId")String reportId);
}
