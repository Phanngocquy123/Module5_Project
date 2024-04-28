package com.ra.project5.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "shopping_cart", schema = "project_module5", catalog = "")
public class ShoppingCartEntity {
    private int shoppingCartId;
    private Integer orderQuantity;
    private ProductsEntity productsByProductId;
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

        if (shoppingCartId != that.shoppingCartId) return false;
        if (orderQuantity != null ? !orderQuantity.equals(that.orderQuantity) : that.orderQuantity != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = shoppingCartId;
        result = 31 * result + (orderQuantity != null ? orderQuantity.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    public ProductsEntity getProductsByProductId() {
        return productsByProductId;
    }

    public void setProductsByProductId(ProductsEntity productsByProductId) {
        this.productsByProductId = productsByProductId;
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
