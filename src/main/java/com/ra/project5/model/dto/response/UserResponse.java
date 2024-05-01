package com.ra.project5.model.dto.response;


import lombok.Data;


@Data
public class UserResponse {
    private String username;
    private String email;
    private String fullName;
    private String avatarUrl;
    private String phone;
    private String address;
}
