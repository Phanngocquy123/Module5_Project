package com.ra.project5.model.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserUpdateRequest {
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private MultipartFile file;
}
