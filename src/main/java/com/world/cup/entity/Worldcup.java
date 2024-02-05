package com.world.cup.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.sql.Date;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Worldcup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int worldcupNum;

    private String title;
    private String description;
    private Byte disclosure;

    @CreatedDate
    @Column(name="regdate",updatable = false)
    private Date regDate;

    @LastModifiedDate
    @Column(name="moddate")
    private Date modDate;
}
