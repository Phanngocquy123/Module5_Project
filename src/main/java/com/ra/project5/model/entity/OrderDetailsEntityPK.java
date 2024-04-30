package com.ra.project5.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

import java.io.Serializable;

public class OrderDetailsEntityPK implements Serializable {
    private long orderId;
    private long productId;

    @Column(name = "order_id")
    @Id
    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    @Column(name = "product_id")
    @Id
    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderDetailsEntityPK that = (OrderDetailsEntityPK) o;

        if (orderId != that.orderId) return false;
        if (productId != that.productId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (orderId ^ (orderId >>> 32));
        result = 31 * result + (int) (productId ^ (productId >>> 32));
        return result;
    }
}
