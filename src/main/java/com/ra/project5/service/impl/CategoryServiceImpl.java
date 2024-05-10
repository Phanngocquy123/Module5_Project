package com.ra.project5.service.impl;

import com.ra.project5.exception.BaseException;
import com.ra.project5.model.dto.request.CategoryRequest;
import com.ra.project5.model.dto.response.CategoryResponse;
import com.ra.project5.model.entity.CategoriesEntity;
import com.ra.project5.repository.CategoryRepository;
import com.ra.project5.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    // 47 - Lấy về danh sách tất cả các danh mục
    @Override
    public List<CategoryResponse> findCategoryAndSort(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<CategoriesEntity> categoryPage = categoryRepository.findAll(pageable);
        List<CategoryResponse> categoryList = categoryPage.getContent().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        return categoryList;
    }

    // 48 - Lấy về thông tin danh mục theo id
    @Override
    public CategoryResponse getCategoryById(Long categoryId) {
        CategoriesEntity categoriesEntity = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new BaseException("RA-48-401"));
        return convertToResponse(categoriesEntity);
    }

    // 49 - Thêm mới danh mục
    @Override
    public CategoryResponse addCategory(CategoryRequest categoryRequest) {
        CategoriesEntity newCategory = new CategoriesEntity();
        newCategory.setCategoryName(categoryRequest.getCategoryName());
        newCategory.setDescription(categoryRequest.getDescription());

        CategoriesEntity savedCategory = categoryRepository.save(newCategory);

        return convertToResponse(savedCategory);
    }

    // 50 - Chỉnh sửa thông tin danh mục
    @Override
    public CategoryResponse updateCategory(Long categoryId, CategoryRequest categoryRequest) {
        CategoriesEntity category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new BaseException("RA-48-401"));

        category.setCategoryName(categoryRequest.getCategoryName());
        category.setDescription(categoryRequest.getDescription());

        CategoriesEntity updatedCategory = categoryRepository.save(category);

        return convertToResponse(updatedCategory);
    }

    // 51 - Xóa danh mục


    @Override
    public void deleteCategory(Long categoryId) {
        CategoriesEntity categoriesEntity = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new BaseException("RA-48-401"));
        categoryRepository.delete(categoriesEntity);
    }

    private CategoryResponse convertToResponse(CategoriesEntity categoriesEntity){
        CategoryResponse response = new CategoryResponse();
        response.setCategoryId(categoriesEntity.getCategoryId());
        response.setCategoryName(categoriesEntity.getCategoryName());
        response.setDescription(categoriesEntity.getDescription());
        return response;
    }
}
