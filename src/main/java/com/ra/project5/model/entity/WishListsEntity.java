package com.ra.project5.model.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "wish_lists", schema = "project_module5", catalog = "")
public class WishListsEntity {
    private long wishListId;
    private UsersEntity usersByUserId;
    private ProductsEntity productsByProductId;

    @Id
    @Column(name = "wish_list_id")
    public long getWishListId() {
        return wishListId;
    }

    public void setWishListId(long wishListId) {
        this.wishListId = wishListId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WishListsEntity that = (WishListsEntity) o;

        if (wishListId != that.wishListId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (wishListId ^ (wishListId >>> 32));
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    public UsersEntity getUsersByUserId() {
        return usersByUserId;
    }

    public void setUsersByUserId(UsersEntity usersByUserId) {
        this.usersByUserId = usersByUserId;
    }

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    public ProductsEntity getProductsByProductId() {
        return productsByProductId;
    }

    public void setProductsByProductId(ProductsEntity productsByProductId) {
        this.productsByProductId = productsByProductId;
    }
}
