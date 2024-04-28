package com.ra.project5.service.impl;

import com.ra.project5.model.dto.response.ProductResponse;
import com.ra.project5.model.entity.ProductsEntity;
import com.ra.project5.repository.ProductRepository;
import com.ra.project5.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<ProductResponse> findProduct(String keyword) {
        List<ProductsEntity> products = productRepository.findByKeyword(keyword);
        return products.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    private ProductResponse convertToResponse(ProductsEntity productEntity) {
        ProductResponse response = new ProductResponse();
        response.setProductId(productEntity.getProductId());
        response.setProductName(productEntity.getProductName());
        response.setDescription(productEntity.getDescription());
        response.setUnitPrice(productEntity.getUnitPrice());
        response.setImage(productEntity.getImage());
        return response;
    }

    @Override
    public List<ProductResponse> findProductAndSort(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<ProductsEntity> productPage = productRepository.findAll(pageable);
        List<ProductResponse> productList = productPage.getContent().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        return productList;
    }
}
