package com.ra.project5.service;

import com.ra.project5.model.dto.request.ShoppingCartRequest;
import com.ra.project5.model.dto.response.ShoppingCartResponse;
import com.ra.project5.model.entity.ShoppingCartEntity;
import com.ra.project5.model.entity.UsersEntity;

import java.math.BigDecimal;
import java.util.List;


public interface ShoppingCartService {
    UsersEntity userUsing();

    List<ShoppingCartResponse> showAll();
    ShoppingCartResponse addToCart(ShoppingCartRequest shoppingCartRequest);
    ShoppingCartResponse updateCartItemQuantity(Integer cartItemId, int quantity);
    void deleteCartItem(Integer cartItemId);
    void deleteAllCartItem();

    BigDecimal totalPrice();
    List<ShoppingCartEntity> findAllListCart();
}
