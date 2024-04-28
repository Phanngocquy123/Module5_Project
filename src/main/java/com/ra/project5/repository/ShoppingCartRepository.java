package com.ra.project5.repository;

import com.ra.project5.model.entity.ProductsEntity;
import com.ra.project5.model.entity.ShoppingCartEntity;
import com.ra.project5.model.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCartEntity, Integer> {
    ShoppingCartEntity findByUsersByUserIdAndProductsByProductId(UsersEntity user, ProductsEntity product);
}
