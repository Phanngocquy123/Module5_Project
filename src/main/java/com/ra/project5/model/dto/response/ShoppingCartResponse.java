package com.ra.project5.model.dto.response;


import lombok.Data;
import java.math.BigDecimal;


@Data
public class ShoppingCartResponse {
    private int shoppingCartId;
    private String productName;
    private BigDecimal unitPrice;
    private Integer orderQuantity;
}
