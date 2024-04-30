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

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public UsersEntity userUsing(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UsersEntity user = userRepository.findByUsername(username);
        return user;
    }

    @Override
    public List<ShoppingCartResponse> showAll() {
        List<ShoppingCartEntity> shoppingCarts = shoppingCartRepository.findByUsersByUserId(userUsing());

        List<ShoppingCartResponse> responses = shoppingCarts.stream()
                .map(shoppingCartEntity -> convertToResponse(shoppingCartEntity, shoppingCartEntity.getProductsByProductId()))
                .collect(Collectors.toList());
        return responses;

    }

    @Override
    public ShoppingCartResponse addToCart(ShoppingCartRequest shoppingCartRequest) {
        // Tìm sản phẩm dựa trên ID
        Optional<ProductsEntity> productOptional = productRepository.findById(shoppingCartRequest.getProductId());
        ProductsEntity product = productOptional.orElseThrow(() -> new BaseException("RA-SP1-404"));

        // Kiểm tra số lượng tồn kho
        if (product.getStockQuantity() < shoppingCartRequest.getOrderQuantity()) {
            throw new BaseException("RA-SP2-400");
        }
        // Trừ số lượng tổn kho
        int remainingStock = product.getStockQuantity() - shoppingCartRequest.getOrderQuantity();
        product.setStockQuantity(remainingStock);
        productRepository.save(product);

        // Kiểm tra trong giỏ hàng đã tồn tại hay chưa
        ShoppingCartEntity existingItem = shoppingCartRepository.findByUsersByUserIdAndProductsByProductId(userUsing(), product);
        if (existingItem != null) {
            existingItem.setOrderQuantity(existingItem.getOrderQuantity() + shoppingCartRequest.getOrderQuantity());
            shoppingCartRepository.save(existingItem);
            return convertToResponse(existingItem, product);
        } else {
            ShoppingCartEntity shoppingCart = new ShoppingCartEntity();
            shoppingCart.setUsersByUserId(userUsing());
            shoppingCart.setProductsByProductId(product);
            shoppingCart.setOrderQuantity(shoppingCartRequest.getOrderQuantity());
            shoppingCartRepository.save(shoppingCart);
            return convertToResponse(shoppingCart, product);
        }
    }

    @Override
    public ShoppingCartResponse updateCartItemQuantity(Integer cartItemId, int quantity) {
              // Tìm kiếm sản phẩm trong giỏ hàng
        Optional<ShoppingCartEntity> cartItemOptional = shoppingCartRepository.findById(cartItemId);
        ShoppingCartEntity cartItem = cartItemOptional.orElseThrow(() -> new BaseException("RA-C18-404"));

        // Kiểm tra xem có phải giỏ hàng của user đang sử dụng hay không
        if (!cartItem.getUsersByUserId().equals(userUsing())) {
            throw new BaseException("RA-C18-403");
        }

        if (quantity <= 0) {
            throw new BaseException("RA-C18-400");
        }
        ProductsEntity product = cartItem.getProductsByProductId();

        // Check lại số lượng trong kho
        int remainingStock = product.getStockQuantity() + cartItem.getOrderQuantity() - quantity;
        if (remainingStock < 0) {
            throw new BaseException("RA-C180-400");
        }

        cartItem.setOrderQuantity(quantity);
        product.setStockQuantity(remainingStock);
        productRepository.save(product);
        shoppingCartRepository.save(cartItem);
        return convertToResponse(cartItem, product);
    }

    @Override
    public void deleteCartItem(Integer cartItemId) {
        // Check có tổn tại trong ShoppingCartEntity không
        ShoppingCartEntity cartItem = shoppingCartRepository.findById(cartItemId)
                .orElseThrow(() -> new BaseException("RA-C19-404"));

        // Kiểm tra xem mục giỏ hàng có thuộc về người dùng hiện tại không
        if (!cartItem.getUsersByUserId().equals(userUsing())) {
            throw new BaseException("RA-C19-403");
        }
        ProductsEntity product = cartItem.getProductsByProductId();
        int orderQuantity = cartItem.getOrderQuantity();

        int remainingStock = product.getStockQuantity() + orderQuantity;
        product.setStockQuantity(remainingStock);
        productRepository.save(product);

        shoppingCartRepository.delete(cartItem);
    }

    @Override
    public void deleteAllCartItem() {
        List<ShoppingCartEntity> userCartItems = shoppingCartRepository.findByUsersByUserId(userUsing());

        for (ShoppingCartEntity cartItem : userCartItems) {
            ProductsEntity product = cartItem.getProductsByProductId();
            int orderQuantity = cartItem.getOrderQuantity();

            int remainingStock = product.getStockQuantity() + orderQuantity;
            product.setStockQuantity(remainingStock);
            productRepository.save(product);
        }
        shoppingCartRepository.deleteAll(userCartItems);
    }

    private ShoppingCartResponse convertToResponse(ShoppingCartEntity shoppingCartEntity, ProductsEntity productsEntity) {
        ShoppingCartResponse response = new ShoppingCartResponse();
        response.setShoppingCartId(shoppingCartEntity.getShoppingCartId());
        response.setProductName(productsEntity.getProductName());
        response.setUnitPrice(productsEntity.getUnitPrice());
        response.setOrderQuantity(shoppingCartEntity.getOrderQuantity());
        return response;
    }
    @Override
    public BigDecimal totalPrice(){
        List<ShoppingCartEntity> shoppingCarts = shoppingCartRepository.findByUsersByUserId(userUsing());
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (ShoppingCartEntity cartEntity : shoppingCarts) {
            BigDecimal productTotalPrice = cartEntity.getProductsByProductId().getUnitPrice()
                    .multiply(BigDecimal.valueOf(cartEntity.getOrderQuantity()));
            totalPrice = totalPrice.add(productTotalPrice);
        }
        return totalPrice;
    }

    @Override
    public List<ShoppingCartEntity> findAllListCart() {
        List<ShoppingCartEntity> shoppingCarts = shoppingCartRepository.findByUsersByUserId(userUsing());
        return shoppingCarts;

    }
}
