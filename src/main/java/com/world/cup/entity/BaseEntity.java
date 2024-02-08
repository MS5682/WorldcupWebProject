package com.world.cup.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@MappedSuperclass
@EntityListeners(value={AuditingEntityListener.class}) // 자동 감지
@Getter
public class BaseEntity {
    @CreatedDate
    @Column(name="regdate",updatable = false)
    private LocalDate regDate;

    @LastModifiedDate
    @Column(name="moddate")
    private LocalDate modDate;
}