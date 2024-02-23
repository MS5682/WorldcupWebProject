package com.world.cup.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

@Data
public class NaverTokenResponse {
    @JsonProperty("access_token")
    private String access_token;


}
