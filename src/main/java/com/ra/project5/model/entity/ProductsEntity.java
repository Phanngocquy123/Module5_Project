package com.ra.project5.model.entity;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;

@Entity
@Table(name = "products", schema = "project_module5", catalog = "")
public class ProductsEntity {
    private long productId;
    private String sku;
    private String productName;
    private String description;
    private BigDecimal unitPrice;
    private Integer stockQuantity;
    private String image;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Collection<OrderDetailsEntity> orderDetailsByProductId;
    private CategoriesEntity categoriesByCategoryId;
    private Collection<ShoppingCartEntity> shoppingCartsByProductId;
    private Collection<WishListsEntity> wishListsByProductId;

    @Id
    @Column(name = "product_id")
    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    @Basic
    @Column(name = "sku")
    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    @Basic
    @Column(name = "product_name")
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "unit_price")
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Basic
    @Column(name = "stock_quantity")
    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    @Basic
    @Column(name = "image")
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
    @Column(name = "updated_at")
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductsEntity that = (ProductsEntity) o;

        if (productId != that.productId) return false;
        if (sku != null ? !sku.equals(that.sku) : that.sku != null) return false;
        if (productName != null ? !productName.equals(that.productName) : that.productName != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (unitPrice != null ? !unitPrice.equals(that.unitPrice) : that.unitPrice != null) return false;
        if (stockQuantity != null ? !stockQuantity.equals(that.stockQuantity) : that.stockQuantity != null)
            return false;
        if (image != null ? !image.equals(that.image) : that.image != null) return false;
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) return false;
        if (updatedAt != null ? !updatedAt.equals(that.updatedAt) : that.updatedAt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (productId ^ (productId >>> 32));
        result = 31 * result + (sku != null ? sku.hashCode() : 0);
        result = 31 * result + (productName != null ? productName.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (unitPrice != null ? unitPrice.hashCode() : 0);
        result = 31 * result + (stockQuantity != null ? stockQuantity.hashCode() : 0);
        result = 31 * result + (image != null ? image.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "productsByProductId")
    public Collection<OrderDetailsEntity> getOrderDetailsByProductId() {
        return orderDetailsByProductId;
    }

    public void setOrderDetailsByProductId(Collection<OrderDetailsEntity> orderDetailsByProductId) {
        this.orderDetailsByProductId = orderDetailsByProductId;
    }

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    public CategoriesEntity getCategoriesByCategoryId() {
        return categoriesByCategoryId;
    }

    public void setCategoriesByCategoryId(CategoriesEntity categoriesByCategoryId) {
        this.categoriesByCategoryId = categoriesByCategoryId;
    }

    @OneToMany(mappedBy = "productsByProductId")
    public Collection<ShoppingCartEntity> getShoppingCartsByProductId() {
        return shoppingCartsByProductId;
    }

    public void setShoppingCartsByProductId(Collection<ShoppingCartEntity> shoppingCartsByProductId) {
        this.shoppingCartsByProductId = shoppingCartsByProductId;
    }

    @OneToMany(mappedBy = "productsByProductId")
    public Collection<WishListsEntity> getWishListsByProductId() {
        return wishListsByProductId;
    }

    public void setWishListsByProductId(Collection<WishListsEntity> wishListsByProductId) {
        this.wishListsByProductId = wishListsByProductId;
    }
}
