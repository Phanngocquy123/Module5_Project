package com.ra.project5.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "wish_lists", schema = "project_module5", catalog = "")
public class WishListsEntity {
    private long wishListId;

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
        return wishListId == that.wishListId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(wishListId);
    }
}
