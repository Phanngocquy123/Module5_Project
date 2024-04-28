package com.ra.project5.controller;

import com.ra.project5.model.dto.request.ShoppingCartRequest;
import com.ra.project5.model.dto.response.ShoppingCartResponse;
import com.ra.project5.model.entity.ShoppingCartEntity;
import com.ra.project5.model.entity.UsersEntity;
import com.ra.project5.repository.UserRoleRepository;
import com.ra.project5.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api.myservice.com/v1/user")
public class RoleUser {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("/shopping-cart")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity addToShoppingCart(@RequestBody ShoppingCartRequest shoppingCartRequest) {
        try {
            // Lấy thông tin người dùng từ token
         //   Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
          //  String username = authentication.getName();

            // Gọi phương thức addToCart từ service để thêm sản phẩm vào giỏ hàng của người dùng
           ShoppingCartResponse response = shoppingCartService.addToCart(shoppingCartRequest);

            // Trả về thông báo thành công nếu không có lỗi xảy ra
          //  return ResponseEntity.ok("Product added to shopping cart successfully.");
            return new  ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e) {
            // Trả về thông báo lỗi nếu có lỗi xảy ra trong quá trình thêm sản phẩm vào giỏ hàng
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add product to shopping cart.");
        }
    }



}
