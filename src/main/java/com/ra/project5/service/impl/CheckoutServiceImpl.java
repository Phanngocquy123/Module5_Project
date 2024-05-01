package com.ra.project5.service.impl;

import com.ra.project5.exception.BaseException;
import com.ra.project5.model.dto.response.CheckoutResponse;

import com.ra.project5.model.entity.*;
import com.ra.project5.repository.*;
import com.ra.project5.service.CheckoutService;
import com.ra.project5.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;

import java.util.List;

@Service
public class CheckoutServiceImpl implements CheckoutService {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private ProductRepository productRepository;


    @Override
    public CheckoutResponse checkout(long addressId) {
        UsersEntity user = shoppingCartService.userUsing();
        AddressesEntity address = addressRepository.findByAddressIdAndUsersByUserId(addressId, user);
        if (address == null) {
            throw new BaseException("RA-C21-400");
        }

        // Tạo đơn hàng
        OrdersEntity order = new OrdersEntity();
        order.setUsersByUserId(user);
        order.setTotalPrice(shoppingCartService.totalPrice());
        order.setStatus("Waiting");
        order.setNote("note");
        order.setReceiveName(address.getReceiveName());
        order.setReceiveAddress(address.getFullAddress());
        order.setReceivePhone(address.getPhone());
        order.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        Timestamp createdAt = order.getCreatedAt();
        LocalDateTime receivedAt = LocalDateTime.ofInstant(createdAt.toInstant(), ZoneId.systemDefault()).plusDays(4);
        Timestamp receivedAtTimestamp = Timestamp.valueOf(receivedAt);
        order.setReceivedAt(receivedAtTimestamp);

        OrdersEntity savedOrder = orderRepository.save(order);

        // Lấy danh sách sản phẩm trong giỏ hàng
        List<ShoppingCartEntity> cartItems = shoppingCartService.findAllListCart();

        // Tạo danh sách chi tiết đơn hàng
        List<OrderDetailsEntity> orderDetailsList = new ArrayList<>();
        for (ShoppingCartEntity cartItem : cartItems) {
            OrderDetailsEntity orderDetail = new OrderDetailsEntity();
            orderDetail.setOrdersByOrderId(savedOrder);
            orderDetail.setProductsByProductId(cartItem.getProductsByProductId());
            orderDetail.setName(cartItem.getProductsByProductId().getProductName());
            orderDetail.setUnitPrice(cartItem.getProductsByProductId().getUnitPrice());
            orderDetail.setOrderQuantity(cartItem.getOrderQuantity());
      //      orderDetailsList.add(orderDetail);
        }

        // Lưu danh sách chi tiết đơn hàng vào cơ sở dữ liệu
     //   orderDetailRepository.saveAll(orderDetailsList);

        // Tạo CheckoutResponse
        CheckoutResponse response = new CheckoutResponse();
        response.setSerialNumber(savedOrder.getSerialNumber());
        response.setTotalPrice(savedOrder.getTotalPrice());
        response.setStatus(savedOrder.getStatus());
        response.setNote(savedOrder.getNote());
        response.setReceiveName(savedOrder.getReceiveName());
        response.setReceiveAddress(savedOrder.getReceiveAddress());
        response.setReceivePhone(savedOrder.getReceivePhone());
        response.setCreatedAt(savedOrder.getCreatedAt());
        response.setReceivedAt(savedOrder.getReceivedAt());

        return response;
    }
}



