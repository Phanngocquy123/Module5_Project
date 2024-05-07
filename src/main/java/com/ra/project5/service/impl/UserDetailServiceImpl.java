package com.ra.project5.service.impl;

import com.ra.project5.exception.BaseException;
import com.ra.project5.model.dto.request.UserRequest;
import com.ra.project5.model.dto.request.UserUpdateRequest;
import com.ra.project5.model.dto.response.UserResponse;
import com.ra.project5.model.entity.RolesEntity;
import com.ra.project5.model.entity.UserRoleEntity;
import com.ra.project5.model.entity.UserRoleEntityPK;
import com.ra.project5.model.entity.UsersEntity;
import com.ra.project5.model.token.UserDetailsAdapter;
import com.ra.project5.repository.RoleRepository;
import com.ra.project5.repository.UserRepository;
import com.ra.project5.repository.UserRoleRepository;
import com.ra.project5.service.FileService;
import com.ra.project5.service.ShoppingCartService;
import com.ra.project5.service.UserService;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailServiceImpl implements UserService, UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    ShoppingCartService shoppingCartService;
    @Autowired
    FileService fileService;



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
    @Transactional(rollbackFor = Exception.class)
    public UsersEntity add(UserRequest userRequest) {
        if (!userRequest.getPassword().equals(userRequest.getConfirmPassword())) {
            throw new BaseException("RA-00-400");
        }

        // Kiểm tra xem các vai trò được cung cấp có tồn tại không
        List<RolesEntity> roles = new ArrayList<>();
        for (String roleName : userRequest.getRoles()) {
            RolesEntity role = roleRepository.findByRoleName(roleName);
            if (role != null) {
                roles.add(role);
            } else {
                throw new BaseException("RA-01-400");
            }
        }
        // Mã hóa mật khẩu
        String encodedPassword = passwordEncoder.encode(userRequest.getPassword());

        UsersEntity user = new UsersEntity();
        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setFullName(userRequest.getFullName());
        user.setStatus(true);
        user.setPassword(encodedPassword);
        user.setPhone(userRequest.getPhone());
        user.setAddress(userRequest.getAddress());
        user.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        UsersEntity savedUser = userRepository.save(user);

        List<UserRoleEntity> userRoleEntities = new ArrayList<>();
        for (RolesEntity role : roles) {
            UserRoleEntity newUserRole = new UserRoleEntity();
            newUserRole.setUserId(savedUser.getUserId());
            newUserRole.setRoleId(role.getRoleId());
            newUserRole.setRolesByRoleId(role);
            newUserRole.setUsersByUserId(savedUser);

            userRoleEntities.add(newUserRole);
            userRoleRepository.save(newUserRole);
        }
        savedUser.setUserRoleEntities(userRoleEntities);
        return savedUser;
    }

    @Override
    public UserResponse showUserAccout() {
        UsersEntity usersEntity = shoppingCartService.userUsing();
        return convertToResponse(usersEntity);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserResponse updateUser(UserUpdateRequest userRequest, MultipartFile file) {
        UsersEntity user = shoppingCartService.userUsing();

        user.setFullName(userRequest.getFullName());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());
        user.setAddress(userRequest.getAddress());

        if (file != null && !file.isEmpty()) {
            try {
                // Lưu tệp tin vào thư mục uploads
                fileService.save(file);
                // Lưu tên tệp tin vào đối tượng người dùng
                user.setAvatar(file.getOriginalFilename());
            } catch (Exception ex) {
                // Xử lý ngoại lệ khi lưu tệp tin
                throw new RuntimeException("Failed to save file", ex);
            }
        }

        UsersEntity updatedUser = userRepository.save(user);
        return convertToResponse(updatedUser);
    }


    // 36 - Lấy ra danh sách người dùng +  phân trang + sắp xếp
    @Override
    public List<UserResponse> getUsers(int page, int size, String sortBy, String sortOrder) {
        Sort.Direction direction = sortOrder.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        Page<UsersEntity> usersPage = userRepository.findAll(pageable);
        return usersPage.getContent().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // 37 - Thêm quyền cho người dùng
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addRoleToUser(long userId, long roleId) {
        // Tìm người dùng dựa trên ID
        UsersEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException("RA-C37-401"));

        // Tìm vai trò dựa trên ID
        RolesEntity role = roleRepository.findById(roleId)
                .orElseThrow(() -> new BaseException("RA-C37-401"));

        // Kiểm tra xem vai trò tồn tại trong cơ sở dữ liệu hay không
//        if (role == null) {
//            throw new BaseException("RA-C37-401"); // Ném ra ngoại lệ nếu vai trò không tồn tại
//        }

        // Tạo một UserRoleEntity mới
        UserRoleEntity newUserRole = new UserRoleEntity();
        newUserRole.setUserId(userId);
        newUserRole.setRoleId(roleId);

        // Lưu thay đổi vào cơ sở dữ liệu
        userRoleRepository.save(newUserRole);
    }

    public UserResponse convertToResponse(UsersEntity usersEntity){
        UserResponse response = new UserResponse();
        response.setUsername(usersEntity.getUsername());
        response.setFullName(usersEntity.getFullName());
        response.setEmail(usersEntity.getEmail());
        response.setPhone(usersEntity.getPhone());
        response.setAddress(usersEntity.getAddress());
        response.setAvatarUrl(usersEntity.getAvatar());
        return response;
    }
}
