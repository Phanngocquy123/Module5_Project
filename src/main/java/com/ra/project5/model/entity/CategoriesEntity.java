package com.ra.project5.model.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "categories", schema = "project_module5", catalog = "")
public class CategoriesEntity {
    private long categoryId;
    private String categoryName;
    private String description;
    private boolean status;

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
        return categoryId == that.categoryId && status == that.status && Objects.equals(categoryName, that.categoryName) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId, categoryName, description, status);
    }
}
