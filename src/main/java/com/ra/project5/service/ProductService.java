package com.ra.project5.service;

import com.ra.project5.model.dto.request.ProductRequest;
import com.ra.project5.model.dto.response.ProductResponse;
import com.ra.project5.model.entity.ProductsEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    List<ProductResponse> findProduct(String key);
    List<ProductResponse> getProductAndSort(int page, int size, String sortBy);

    ProductResponse getProductById(Long productId);
    ProductResponse addNewProduct(ProductRequest request, MultipartFile file);
    void deleteProduct(Long productId);


}
