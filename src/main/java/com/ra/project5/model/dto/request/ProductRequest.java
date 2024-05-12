package com.ra.project5.model.dto.request;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;


@Data
public class ProductRequest {
    private String productName;
    private String description;
    private BigDecimal unitPrice;
    private Integer stockQuantity;
    private MultipartFile image;
    private long categoryId;
}
