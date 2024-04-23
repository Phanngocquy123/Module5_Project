package com.ra.project5.model.token;

import lombok.Data;

@Data
public class TokenResponse {
    private String token;
    private String tokenType = "Bearer";
}
