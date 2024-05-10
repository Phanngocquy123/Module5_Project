package com.ra.project5.model.dto.request;

import com.ra.project5.model.entity.CategoriesEntity;
import com.ra.project5.model.entity.OrderDetailsEntity;
import com.ra.project5.model.entity.ShoppingCartEntity;
import com.ra.project5.model.entity.WishListsEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;

@Data
public class ProductRequest {
    private String productName;
    private String description;
    private BigDecimal unitPrice;
    private Integer stockQuantity;
    private String image;
    private long categoryId;
}
