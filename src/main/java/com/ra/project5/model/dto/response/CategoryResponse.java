package com.ra.project5.model.dto.response;

import lombok.Data;

@Data
public class CategoryResponse {
    private long categoryId;
    private String categoryName;
    private String description;
}
