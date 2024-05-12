package com.ra.project5.service;

import com.ra.project5.model.dto.request.UserRequest;
import com.ra.project5.model.dto.request.UserUpdateRequest;
import com.ra.project5.model.dto.response.RoleResponse;
import com.ra.project5.model.dto.response.UserResponse;
import com.ra.project5.model.entity.UsersEntity;
import com.ra.project5.model.token.TokenRequest;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface UserService {
    UsersEntity add(UserRequest userRequest);

    UserResponse showUserAccout();
    UserResponse updateUser(UserUpdateRequest userRequest, MultipartFile file);




    // admin
    List<UserResponse> getUsers(int page, int size, String sortBy, String sortOrder);
    void addRoleToUser(long userId, long roleId);
    void removeRoleFromUser(long userId, long roleId);
    void activeAndBlockUserStatus(long userId);
    List<RoleResponse> getAllUserRoles();
    List<UserResponse> searchUsersByName(String name);
}
