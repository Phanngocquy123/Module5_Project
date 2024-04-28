package com.ra.project5.service;

import com.ra.project5.model.dto.request.ShoppingCartRequest;
import com.ra.project5.model.dto.response.ShoppingCartResponse;
import com.ra.project5.model.entity.UsersEntity;


public interface ShoppingCartService {
    ShoppingCartResponse addToCart(ShoppingCartRequest shoppingCartRequest);
}
