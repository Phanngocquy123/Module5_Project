package com.ra.project5.controller;

import com.ra.project5.model.dto.request.CategoryRequest;
import com.ra.project5.model.dto.request.ProductRequest;
import com.ra.project5.model.dto.response.*;
import com.ra.project5.model.entity.UsersEntity;
import com.ra.project5.service.CategoryService;
import com.ra.project5.service.ProductService;
import com.ra.project5.service.UserRoleService;
import com.ra.project5.service.UserService;
import com.ra.project5.service.impl.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api.myservice.com/v1/admin")
public class RoleAdmin {
    @Autowired
    private ProductService productService;
    @Autowired
    private UserDetailServiceImpl userDetailService;
    @Autowired
    private CategoryService categoryService;

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

    // 38 - Xóa quyền người dùng
    @DeleteMapping("/users/{userId}/role/{roleId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<NoticeResponse> removeRoleFromUser(@PathVariable long userId, @PathVariable long roleId) {
        userDetailService.removeRoleFromUser(userId, roleId);
        NoticeResponse response = new NoticeResponse("Remove permission success !");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 39 - Mở/ khóa người dùng
    @PutMapping("/users/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<NoticeResponse> toggleUserStatus(@PathVariable long userId) {
        userDetailService.activeAndBlockUserStatus(userId);
        NoticeResponse response = new NoticeResponse("Block/Unblock user success !");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 40 - Lấy về danh sách quyền
    @GetMapping("/roles")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<RoleResponse>> getAllUserRoles() {
        List<RoleResponse> roleResponses = userDetailService.getAllUserRoles();
        if (roleResponses != null) {
            return ResponseEntity.ok(roleResponses);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // 41 - Tìm kiếm người dùng theo tên
    @GetMapping("/users/search")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponse>> searchUsersByName(@RequestParam String name) {
        List<UserResponse> users = userDetailService.searchUsersByName(name);
        return ResponseEntity.ok(users);
    }

    // 42 - Lấy về danh sách tất cả sản phẩm, sắp xếp, phân trang
    @GetMapping("/products")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ProductResponse>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size,
            @RequestParam(defaultValue = "productId") String sortBy) {
        List<ProductResponse> products = productService.getProductAndSort(page, size, sortBy);
        return ResponseEntity.ok(products);
    }

    // 43 - Lấy thông tin sản phẩm theo id
    @GetMapping("/products/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long productId) {
        ProductResponse product = productService.getProductById(productId);
        return ResponseEntity.ok(product);
    }

    // 44 - Thêm mới sản phẩm
    @PostMapping(value = "/products", consumes = {"multipart/form-data", "application/*", MediaType.MULTIPART_FORM_DATA_VALUE})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponse> addNewProduct(@RequestPart ProductRequest request,
                                                         @RequestPart("file") MultipartFile file) {
        ProductResponse response = productService.addNewProduct(request, file);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 45 - Chỉnh sửa thông tin sản phẩm
    @PutMapping(value = "/products/{productId}", consumes = {"multipart/form-data", "application/*", MediaType.MULTIPART_FORM_DATA_VALUE})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long productId,
                                                         @RequestPart ProductRequest request,
                                                         @RequestPart("file") MultipartFile file) {
        ProductResponse response = productService.updateProduct(productId,request, file);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 46 - Xóa sản phẩm theo id
    @DeleteMapping("/products/delete/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<NoticeResponse> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        NoticeResponse response = new NoticeResponse("Delete successfully !");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 47 - Lấy về danh sách tất cả các danh muc + sắp xếp + phân trang
    @GetMapping("/categories")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CategoryResponse>> getAllCategories(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String sortBy
    ) {
        List<CategoryResponse> categories = categoryService.findCategoryAndSort(page, size, sortBy);
        return ResponseEntity.ok(categories);
    }

    // 48 - Lấy về thông tin danh mục theo id
    @GetMapping("/categories/{categoryId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long categoryId) {
        CategoryResponse category = categoryService.getCategoryById(categoryId);
        return ResponseEntity.ok(category);
    }

    // 49 - Thêm mới danh mục
    @PostMapping("/categories")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryResponse> addCategory(@RequestBody CategoryRequest categoryRequest) {
        CategoryResponse response = categoryService.addCategory(categoryRequest);
        return ResponseEntity.ok(response);
    }

    // 50 - Chỉnh sửa thông tin danh mục
    @PutMapping("/categories/{categoryId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryResponse> updateCategory(
            @PathVariable Long categoryId,
            @RequestBody CategoryRequest categoryRequest) {
        CategoryResponse updatedCategory = categoryService.updateCategory(categoryId, categoryRequest);
        return ResponseEntity.ok(updatedCategory);
    }

    // 51 - Xóa danh mục
    @DeleteMapping("/categories/{categoryId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<NoticeResponse> deleteCategoryById(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        NoticeResponse response = new NoticeResponse("Delete success !");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
