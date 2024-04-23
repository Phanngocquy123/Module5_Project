package com.ra.project5.service;

import com.ra.project5.model.dto.request.UserRequest;
import com.ra.project5.model.entity.UsersEntity;
import com.ra.project5.model.token.TokenRequest;

public interface UserService {
    UsersEntity add(UserRequest userRequest);
}
