package com.ra.project5.model.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class CheckoutResponse {
    private String serialNumber;
    private BigDecimal totalPrice;
    private Object status;
    private String note;
    private String receiveName;
    private String receiveAddress;
    private String receivePhone;
    private Timestamp createdAt;
    private Timestamp receivedAt;
}
