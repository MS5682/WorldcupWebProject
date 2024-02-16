package com.world.cup.repository;

import com.world.cup.entity.Choice;
import com.world.cup.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {

}
