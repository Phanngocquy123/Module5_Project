package com.ra.project5.model.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "roles", schema = "project_module5", catalog = "")
public class RolesEntity {
    private long roleId;
    private String roleName;

    @Id
    @Column(name = "role_id")
    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    @Basic
    @Column(name = "role_name")
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }


}
