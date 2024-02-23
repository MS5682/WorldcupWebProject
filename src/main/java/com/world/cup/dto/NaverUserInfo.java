package com.world.cup.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NaverUserInfo {
    @JsonProperty("id")
    private String id;

    @JsonProperty("email")
    private String email;
}
