package com.ra.project5.service;

import com.ra.project5.model.dto.request.UserRequest;
import com.ra.project5.model.dto.request.UserUpdateRequest;
import com.ra.project5.model.dto.response.UserResponse;
import com.ra.project5.model.entity.UsersEntity;
import com.ra.project5.model.token.TokenRequest;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    UsersEntity add(UserRequest userRequest);

    UserResponse showUserAccout();
    UserResponse updateUser(UserUpdateRequest userRequest, MultipartFile file);
}
