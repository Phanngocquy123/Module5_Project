package com.ra.project5.service.impl;

import com.ra.project5.model.dto.request.UserRequest;
import com.ra.project5.model.entity.RolesEntity;
import com.ra.project5.model.entity.UserRoleEntity;
import com.ra.project5.model.entity.UsersEntity;
import com.ra.project5.model.token.TokenRequest;
import com.ra.project5.model.token.UserDetailsAdapter;
import com.ra.project5.repository.RoleRepository;
import com.ra.project5.repository.UserRepository;
import com.ra.project5.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailServiceImpl implements UserService, UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsersEntity usersEntity = userRepository.findByUsername(username);
        if (usersEntity != null) {
            UserDetails userDetails = new UserDetailsAdapter(usersEntity);
            return userDetails;
        }
        throw new UsernameNotFoundException("Username \"" + username + "\" not found!");
    }

    @Override
    public UsersEntity add(UserRequest userRequest) {
// Kiểm tra xem username đã tồn tại trong cơ sở dữ liệu chưa
//        if (userRepository.existsByUsername(userRequest.getUsername())) {
//            throw new RuntimeException("Username \"" + userRequest.getUsername() + "\" đã được sử dụng!");
//        }



        // Mã hóa mật khẩu trước khi lưu vào cơ sở dữ liệu
        String encodedPassword = passwordEncoder.encode(userRequest.getPassword());

        // Tạo đối tượng UsersEntity từ thông tin trong userRequest
        UsersEntity user = new UsersEntity();
        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setFullName(userRequest.getFullName());
        user.setStatus(true);
        user.setPassword(encodedPassword); // Lưu mật khẩu đã mã hóa
        user.setPhone(userRequest.getPhone());
        user.setAddress(userRequest.getAddress());
        user.setCreatedAt(new Timestamp(System.currentTimeMillis())); // Đặt thời gian tạo mới



        // Tạo danh sách vai trò từ thông tin được cung cấp trong request
        List<RolesEntity> rolesEntities = new ArrayList<>();

        // Lấy danh sách vai trò từ cơ sở dữ liệu
        List<RolesEntity> allRoles = roleRepository.findAll();

        // Kiểm tra và lọc ra các vai trò từ danh sách tất cả vai trò
//        for (RolesEntity role : allRoles) {
//            if (userRequest.getRoles().contains(role.getRoleName())) {
//                user.setUserRoleEntities(createUserRoleEntities(user, rolesEntities));
//            }
//        }

        // Gán danh sách vai trò cho người dùng


        // Lưu người dùng vào cơ sở dữ liệu
        return userRepository.save(user);
    }

    // Tạo danh sách UserRoleEntity từ danh sách người dùng và vai trò tương ứng
    private List<UserRoleEntity> createUserRoleEntities(UsersEntity user, List<RolesEntity> roles) {
        List<UserRoleEntity> userRoleEntities = new ArrayList<>();
        for (RolesEntity role : roles) {
            UserRoleEntity userRoleEntity = new UserRoleEntity();
            userRoleEntity.setUsersByUserId(user);
            userRoleEntity.setRolesByRoleId(role);
            userRoleEntities.add(userRoleEntity);
        }
        return userRoleEntities;
    }
}
