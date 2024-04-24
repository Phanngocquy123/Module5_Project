package com.ra.project5.service.impl;

import com.ra.project5.model.entity.RolesEntity;
import com.ra.project5.repository.RoleRepository;
import com.ra.project5.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public RolesEntity findRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }
}
