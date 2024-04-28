package com.ra.project5.model.dto.response;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class ProductResponse {
    private long productId;
    private String productName;
    private String description;
    private BigDecimal unitPrice;
    private String image;
}
