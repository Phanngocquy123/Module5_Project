package com.ra.project5.model.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class RoleResponse {
    private long id;
    private List<String> roleName;
}
