package com.world.cup.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GoogleDTO {
    private String gid;
    private String email;
    private String userRole;

}
