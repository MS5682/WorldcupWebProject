package com.world.cup.dto;

import jakarta.persistence.Column;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.sql.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WorldcupDTO {
    private int worldcupNum;
    private String title;
    private String description;
    private Byte disclosure;
    private Date regDate;
    private Date modDate;

    private List<ChoiceDTO> choice;
}
