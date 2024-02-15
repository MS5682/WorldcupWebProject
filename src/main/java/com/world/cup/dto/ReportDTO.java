package com.world.cup.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReportDTO {

    private int reportId;

    private int worldcupNum;

    private String worldcupTitle;

    private String reporter;

    private LocalDateTime regDate;

    private String reportContent;


}
