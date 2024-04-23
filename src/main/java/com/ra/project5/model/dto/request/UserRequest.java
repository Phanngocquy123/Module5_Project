package com.ra.project5.model.dto.request;


import com.ra.project5.model.entity.RolesEntity;
import lombok.Data;

import java.util.List;

@Data
public class UserRequest {
    private String username;
    private String password;
    private String confirmPassword;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private String roles;
}
