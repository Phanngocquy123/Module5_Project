package com.ra.project5.model.dto.request;


import lombok.Data;

@Data
public class ShoppingCartRequest {
    private long productId;
    private Integer orderQuantity;
}
