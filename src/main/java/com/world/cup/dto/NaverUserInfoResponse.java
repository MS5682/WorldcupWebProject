package com.world.cup.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

@Data
public class NaverUserInfoResponse {
    @JsonProperty("resultcode")
    private String resultcode;

    @JsonProperty("message")
    private String message;

    @JsonProperty("response")
    private NaverUserInfo response;
}
