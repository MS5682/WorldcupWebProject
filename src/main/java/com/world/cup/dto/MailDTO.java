package com.world.cup.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MailDTO {
    private String address;
    private String title;
    private String message;
}
