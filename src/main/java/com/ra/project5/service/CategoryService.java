package com.ra.project5.service;

import com.ra.project5.model.dto.request.CategoryRequest;
import com.ra.project5.model.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {
    List<CategoryResponse> findCategoryAndSort(int page, int size, String sortBy);
    CategoryResponse getCategoryById(Long categoryId);
    CategoryResponse addCategory(CategoryRequest categoryRequest);
    CategoryResponse updateCategory(Long categoryId, CategoryRequest categoryRequest);
    void deleteCategory(Long categoryId);
}
