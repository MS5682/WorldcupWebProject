package com.world.cup.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Date;
import java.time.LocalDateTime;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(value = { AuditingEntityListener.class })
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(nullable = false, length = 20)
    private String id;

    @Column(nullable = false, length = 40,unique = true)
    private String email;

    @Column(nullable = false, length =40)
    private String password;


    @CreatedDate
    @Column(name = "regdate", updatable = false)
    private LocalDateTime regDate;

    @Column(name="userrole",length = 20)
    private String userRole;

}
