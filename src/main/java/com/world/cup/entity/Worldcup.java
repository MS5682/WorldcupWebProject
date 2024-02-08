package com.world.cup.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Date;
import java.time.LocalDate;

@Entity
@EntityListeners(value={AuditingEntityListener.class})
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Worldcup{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int worldcupNum;

    private String title;
    private String description;
    private Byte disclosure;


    private int viewCnt;

    @CreatedDate
    @Column(name="regdate",updatable = false)
    private LocalDate regDate;

    @LastModifiedDate
    @Column(name="moddate")
    private LocalDate modDate;
}
