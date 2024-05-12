package com.ra.project5.service;

import com.ra.project5.model.dto.response.CheckoutResponse;

public interface CheckoutService {
    CheckoutResponse checkOut(long addressId);
}
