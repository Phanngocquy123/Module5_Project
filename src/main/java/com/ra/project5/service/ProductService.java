package com.ra.project5.service;

import com.ra.project5.model.dto.response.ProductResponse;
import com.ra.project5.model.entity.ProductsEntity;

import java.util.List;

public interface ProductService {
    List<ProductResponse> findProduct(String key);
    List<ProductResponse> findProductAndSort(int page, int size, String sortBy);

    void deleteProduct(Long productId);

}
