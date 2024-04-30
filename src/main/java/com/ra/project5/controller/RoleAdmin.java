package com.ra.project5.controller;

import com.ra.project5.model.dto.response.NoticeResponse;
import com.ra.project5.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api.myservice.com/v1/admin")
public class RoleAdmin {
    @Autowired
    private ProductService productService;

    // 46 - Xóa sản phẩm theo id
    @DeleteMapping("/products/delete/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<NoticeResponse> deleteProduct(@PathVariable Long productId){
        productService.deleteProduct(productId);
        NoticeResponse response = new NoticeResponse("Delete successfully !");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
