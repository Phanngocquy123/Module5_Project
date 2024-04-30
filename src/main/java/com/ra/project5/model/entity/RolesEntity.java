package com.ra.project5.model.entity;

import jakarta.persistence.*;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RolesEntity rolesEntity = (RolesEntity) o;

        if (roleId != rolesEntity.roleId) return false;
        if (roleName != null ? !roleName.equals(rolesEntity.roleName) : rolesEntity.roleName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (roleId ^ (roleId >>> 32));
        result = 31 * result + (roleName != null ? roleName.hashCode() : 0);
        return result;
    }
}
