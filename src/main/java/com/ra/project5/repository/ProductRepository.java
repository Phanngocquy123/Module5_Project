package com.ra.project5.repository;


import com.ra.project5.model.dto.response.ProductResponse;
import com.ra.project5.model.entity.ProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductsEntity, Long> {
    @Query("SELECT p FROM ProductsEntity p WHERE LOWER(p.productName) LIKE %:keyword% OR LOWER(p.description) LIKE %:keyword%")
    List<ProductsEntity> findByKeyword(String keyword);

    ProductsEntity findByProductName(String productName);
}