package com.ra.project5.service.impl;

import com.ra.project5.exception.BaseException;
import com.ra.project5.model.dto.request.ShoppingCartRequest;
import com.ra.project5.model.dto.response.ShoppingCartResponse;
import com.ra.project5.model.entity.ProductsEntity;
import com.ra.project5.model.entity.ShoppingCartEntity;
import com.ra.project5.model.entity.UsersEntity;
import com.ra.project5.repository.ProductRepository;
import com.ra.project5.repository.ShoppingCartRepository;
import com.ra.project5.repository.UserRepository;
import com.ra.project5.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public void addToCart(ShoppingCartRequest shoppingCartRequest) {
        // Lấy thông tin người dùng từ token
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Tìm người dùng dựa trên tên người dùng
        UsersEntity user = userRepository.findByUsername(username);
        if (user == null) {
           //  throw new BaseException("RA-02-404");
            System.out.println("aaa");
        }

        // Tìm sản phẩm dựa trên ID
        Optional<ProductsEntity> productOptional = productRepository.findById(shoppingCartRequest.getProductId());
        ProductsEntity product = productOptional.orElseThrow(() -> new BaseException("RA-03-404"));

        // Kiểm tra số lượng tồn kho của sản phẩm
        if (product.getStockQuantity() < shoppingCartRequest.getOrderQuantity()) {
            throw new BaseException("RA-04-400");
        }

        // Kiểm tra xem mục trong giỏ hàng đã tồn tại hay chưa
        ShoppingCartEntity existingItem = shoppingCartRepository.findByUsersByUserIdAndProductsByProductId(user, product);
       if (existingItem != null) {
            // Nếu đã tồn tại, cập nhật số lượng
            existingItem.setOrderQuantity(existingItem.getOrderQuantity() + shoppingCartRequest.getOrderQuantity());
            shoppingCartRepository.save(existingItem);
       } else {
            // Nếu chưa tồn tại, tạo một mục mới
            ShoppingCartEntity shoppingCart = new ShoppingCartEntity();
            shoppingCart.setUsersByUserId(user);
            shoppingCart.setProductsByProductId(product);
            shoppingCart.setOrderQuantity(shoppingCartRequest.getOrderQuantity());
            shoppingCartRepository.save(shoppingCart);
        }
    }
}
