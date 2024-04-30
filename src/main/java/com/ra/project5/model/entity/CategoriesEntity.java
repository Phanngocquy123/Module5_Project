package com.ra.project5.model.entity;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "categories", schema = "project_module5", catalog = "")
public class CategoriesEntity {
    private long categoryId;
    private String categoryName;
    private String description;
    private boolean status;
    private Collection<ProductsEntity> productsByCategoryId;

    @Id
    @Column(name = "category_id")
    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    @Basic
    @Column(name = "category_name")
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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
    @Column(name = "status")
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CategoriesEntity that = (CategoriesEntity) o;

        if (categoryId != that.categoryId) return false;
        if (status != that.status) return false;
        if (categoryName != null ? !categoryName.equals(that.categoryName) : that.categoryName != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (categoryId ^ (categoryId >>> 32));
        result = 31 * result + (categoryName != null ? categoryName.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (status ? 1 : 0);
        return result;
    }

    @OneToMany(mappedBy = "categoriesByCategoryId")
    public Collection<ProductsEntity> getProductsByCategoryId() {
        return productsByCategoryId;
    }

    public void setProductsByCategoryId(Collection<ProductsEntity> productsByCategoryId) {
        this.productsByCategoryId = productsByCategoryId;
    }
}
