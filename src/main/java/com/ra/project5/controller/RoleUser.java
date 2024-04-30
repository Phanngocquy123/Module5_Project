package com.ra.project5.controller;


import com.ra.project5.model.dto.request.CheckoutRequest;
import com.ra.project5.model.dto.request.ShoppingCartRequest;
import com.ra.project5.model.dto.response.CheckoutResponse;
import com.ra.project5.model.dto.response.NoticeResponse;
import com.ra.project5.model.dto.response.ShoppingCartResponse;

import com.ra.project5.service.CheckoutService;
import com.ra.project5.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api.myservice.com/v1/user")
public class RoleUser {
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private CheckoutService checkoutService;

    // 16 - Danh sách sản phẩm trong giỏ hàng
    @GetMapping("/shopping-cart/show")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<ShoppingCartResponse>> showAll(){
        return new ResponseEntity<>(shoppingCartService.showAll(), HttpStatus.OK);
    }

    // 17 - Thêm mới sản phẩm vào giỏ hàng
    @PostMapping("/shopping-cart/add")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity addToShoppingCart(@RequestBody ShoppingCartRequest shoppingCartRequest) {
        ShoppingCartResponse response = shoppingCartService.addToCart(shoppingCartRequest);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    // 18 - Thay đổi số lượng đặt hàng của 1 sản phẩm
    @PutMapping("/shopping-cart/{cartItemId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity updateCartItemQuantity(@PathVariable Integer cartItemId, @RequestBody Map<String, Integer> requestBody) {
        int quantity = requestBody.get("quantity");
        ShoppingCartResponse response = shoppingCartService.updateCartItemQuantity(cartItemId, quantity);
        return new ResponseEntity(response, HttpStatus.OK);
    }
    // 19 - Xóa 1 sp trong giỏ hàng
    @DeleteMapping("/shopping-cart/delete/{cartItemId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<NoticeResponse> deleteCartItem(@PathVariable Integer cartItemId) {
        shoppingCartService.deleteCartItem(cartItemId);
        NoticeResponse response = new NoticeResponse("Delete successfully !");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 20 -  Xóa toàn bộ sản phẩm
    @DeleteMapping("/shopping-cart/delete-all")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<NoticeResponse> clearCart() {
        shoppingCartService.deleteAllCartItem();
        NoticeResponse response = new NoticeResponse("Delete successfully !");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 21 - Đặt hàng
    @PostMapping("/shopping-cart/checkout")
    @PreAuthorize("hasRole('USER')")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<CheckoutResponse> checkout(@RequestBody CheckoutRequest request) {
        long addressId = request.getAddressId();
        CheckoutResponse response = checkoutService.checkout(addressId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
