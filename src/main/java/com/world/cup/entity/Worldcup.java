package com.world.cup.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private LocalDateTime regDate;

    @CreatedDate
    @LastModifiedDate
    @Column(name="moddate")
    private LocalDateTime modDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public void changeTitle(String title){
        this.title = title;
    }
    public void changeDescription(String description){
        this.description = description;
    }
    public void changeDisclosure(Byte disclosure){
        this.disclosure = disclosure;
    }
    public void changeViewCnt(int viewCnt){
        this.viewCnt = viewCnt;
    }

}
