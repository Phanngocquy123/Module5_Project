package com.ra.project5.repository;

import com.ra.project5.model.entity.UserRoleEntity;
import com.ra.project5.model.entity.UserRoleEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, UserRoleEntityPK> {
    UserRoleEntity findByUserIdAndRoleId(long userId, long roleId);
}
