package com.ra.project5.model.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "orders", schema = "project_module5", catalog = "")
public class OrdersEntity {
    private long orderId;
    private String serialNumber;
    private long userId;
    private BigDecimal totalPrice;
    private Object status;
    private String note;
    private String receiveName;
    private String receiveAddress;
    private String receivePhone;
    private Timestamp createdAt;
    private Date receivedAt;

    @Id
    @Column(name = "order_id")
    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    @Basic
    @Column(name = "serial_number")
    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    @Basic
    @Column(name = "user_id")
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "total_price")
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Basic
    @Column(name = "status")
    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }

    @Basic
    @Column(name = "note")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Basic
    @Column(name = "receive_name")
    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    @Basic
    @Column(name = "receive_address")
    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    @Basic
    @Column(name = "receive_phone")
    public String getReceivePhone() {
        return receivePhone;
    }

    public void setReceivePhone(String receivePhone) {
        this.receivePhone = receivePhone;
    }

    @Basic
    @Column(name = "created_at")
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Basic
    @Column(name = "received_at")
    public Date getReceivedAt() {
        return receivedAt;
    }

    public void setReceivedAt(Date receivedAt) {
        this.receivedAt = receivedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrdersEntity that = (OrdersEntity) o;
        return orderId == that.orderId && userId == that.userId && Objects.equals(serialNumber, that.serialNumber) && Objects.equals(totalPrice, that.totalPrice) && Objects.equals(status, that.status) && Objects.equals(note, that.note) && Objects.equals(receiveName, that.receiveName) && Objects.equals(receiveAddress, that.receiveAddress) && Objects.equals(receivePhone, that.receivePhone) && Objects.equals(createdAt, that.createdAt) && Objects.equals(receivedAt, that.receivedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, serialNumber, userId, totalPrice, status, note, receiveName, receiveAddress, receivePhone, createdAt, receivedAt);
    }
}
