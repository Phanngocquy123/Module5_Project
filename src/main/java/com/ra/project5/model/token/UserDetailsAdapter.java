package com.ra.project5.model.token;

import com.ra.project5.model.entity.UserRoleEntity;
import com.ra.project5.model.entity.UsersEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserDetailsAdapter implements UserDetails {
    private UsersEntity usersEntity;

    public UserDetailsAdapter(UsersEntity usersEntity) {
        this.usersEntity = usersEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        // Kiểm tra xem usersEntity có null không
        if (usersEntity != null) {
            usersEntity.getUserRoleEntities().forEach(ur -> {
                String roleName = ur.getRolesByRoleId().getRoleName();
                if (roleName != null) {
                    authorities.add(new SimpleGrantedAuthority(roleName));
                }
            });
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.usersEntity != null ? this.usersEntity.getPassword() : null;
    }

    @Override
    public String getUsername() {
        return this.usersEntity != null ? this.usersEntity.getUsername() : null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.usersEntity != null && this.usersEntity.isStatus();
    }


}
