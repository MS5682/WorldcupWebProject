package com.world.cup.dto;

import jakarta.persistence.Column;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;


import java.time.LocalDate;
import java.util.ArrayList;
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
    private LocalDate regDate;
    private LocalDate modDate;
    @Builder.Default
    private List<ChoiceDTO> choice = new ArrayList<>();;
    private String id;
}
