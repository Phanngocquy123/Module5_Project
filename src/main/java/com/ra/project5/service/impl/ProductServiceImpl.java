package com.ra.project5.service.impl;

import com.ra.project5.exception.BaseException;
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

    // 10 - Danh sách sản phẩm có bán
    // 42 - Lấy về danh sách tất cả sản phẩm, sắp xếp, phân trang
    @Override
    public List<ProductResponse> findProductAndSort(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<ProductsEntity> productPage = productRepository.findAll(pageable);
        List<ProductResponse> productList = productPage.getContent().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        return productList;
    }

    // 43 - Lấy thông tin sản phẩm theo id
    @Override
    public ProductResponse getProductById(Long productId) {
        ProductsEntity productEntity = productRepository.findById(productId)
                .orElseThrow(() -> new BaseException("RA-43-401"));

        return convertToResponse(productEntity);
    }

    // 44 - Thêm mới sản phẩm
    // 45 - Chỉnh sửa thông tin sản phẩm

    // 46 xóa sản phẩm
    @Override
    public void deleteProduct(Long productId){
        ProductsEntity productsEntity = productRepository.findById(productId)
                .orElseThrow(() -> new BaseException("RA-C46-404"));

        productRepository.delete(productsEntity);
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

}
