package com.ra.project5.service.impl;

import com.ra.project5.model.entity.UserRoleEntity;
import com.ra.project5.repository.UserRoleRepository;
import com.ra.project5.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Override
    public UserRoleEntity add(UserRoleEntity userRoleEntity) {
        return userRoleRepository.save(userRoleEntity);
    }
}
