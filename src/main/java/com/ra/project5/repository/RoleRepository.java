package com.ra.project5.repository;

import com.ra.project5.model.entity.RolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RolesEntity, Long> {
}
