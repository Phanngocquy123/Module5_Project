package com.ra.project5.controller;

import com.ra.project5.model.dto.response.NoticeResponse;
import com.ra.project5.model.dto.response.UserResponse;
import com.ra.project5.service.ProductService;
import com.ra.project5.service.UserService;
import com.ra.project5.service.impl.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api.myservice.com/v1/admin")
public class RoleAdmin {
    @Autowired
    private ProductService productService;
    @Autowired
    private UserDetailServiceImpl userDetailService;

    // 36 - Lấy ra danh sách người dùng +  phân trang + sắp xếp
    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponse>> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size,
            @RequestParam(defaultValue = "username") String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrder) {
        List<UserResponse> users = userDetailService.getUsers(page, size, sortBy, sortOrder);
        return ResponseEntity.ok(users);
    }

    // 37 - Thêm quyền cho người dùng
    @PostMapping("/users/{userId}/role/{roleId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<NoticeResponse> assignRoleToUser(@PathVariable long userId, @PathVariable long roleId) {
        userDetailService.addRoleToUser(userId, roleId);
        NoticeResponse response = new NoticeResponse("Add permission success !");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }







    // 46 - Xóa sản phẩm theo id
    @DeleteMapping("/products/delete/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<NoticeResponse> deleteProduct(@PathVariable Long productId){
        productService.deleteProduct(productId);
        NoticeResponse response = new NoticeResponse("Delete successfully !");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
