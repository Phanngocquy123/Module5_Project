package com.ra.project5.model.token;

import lombok.Data;

import java.util.List;

@Data
public class TokenResponse {
    private long userId;
    private String userName;
    private String token;
    private String tokenType = "Bearer Token";
    private List<String> roles;

}
