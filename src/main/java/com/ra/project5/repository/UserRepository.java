package com.ra.project5.repository;

import com.ra.project5.model.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UsersEntity, Long> {
    UsersEntity findByUsername(String username);
    List<UsersEntity> findByFullNameContainingIgnoreCase(String name);

}
