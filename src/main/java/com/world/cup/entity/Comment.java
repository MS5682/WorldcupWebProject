package com.world.cup.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(value={AuditingEntityListener.class})
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commentNum;

    private String content;
    private int type;
    @ManyToOne(fetch = FetchType.LAZY)
    private Worldcup worldcup;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Choice choice;

    @CreatedDate
    @Column(name="regdate",updatable = false)
    private LocalDateTime regDate;

    public void changeContent(String content){
        this.content = content;
    }

}
