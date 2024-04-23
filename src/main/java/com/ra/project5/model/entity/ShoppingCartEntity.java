package com.ra.project5.model.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "shopping_cart", schema = "project_module5", catalog = "")
public class ShoppingCartEntity {
    private int shoppingCartId;
    private Integer orderQuantity;
    private UsersEntity usersByUserId;

    @Id
    @Column(name = "shopping_cart_id")
    public int getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(int shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    @Basic
    @Column(name = "order_quantity")
    public Integer getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(Integer orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShoppingCartEntity that = (ShoppingCartEntity) o;
        return shoppingCartId == that.shoppingCartId && Objects.equals(orderQuantity, that.orderQuantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shoppingCartId, orderQuantity);
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    public UsersEntity getUsersByUserId() {
        return usersByUserId;
    }

    public void setUsersByUserId(UsersEntity usersByUserId) {
        this.usersByUserId = usersByUserId;
    }
}
