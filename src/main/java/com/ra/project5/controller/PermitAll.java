package com.ra.project5.controller;

import com.ra.project5.exception.BaseException;
import com.ra.project5.model.dto.request.ShoppingCartRequest;
import com.ra.project5.model.dto.request.UserRequest;
import com.ra.project5.model.dto.response.ProductResponse;
import com.ra.project5.model.dto.response.RegistrationResponse;
import com.ra.project5.model.dto.response.ShoppingCartResponse;
import com.ra.project5.model.entity.UsersEntity;
import com.ra.project5.model.token.TokenRequest;
import com.ra.project5.model.token.TokenResponse;
import com.ra.project5.model.token.UserDetailsAdapter;
import com.ra.project5.service.ShoppingCartService;
import com.ra.project5.service.impl.ProductServiceImpl;
import com.ra.project5.service.impl.UserDetailServiceImpl;
import com.ra.project5.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api.myservice.com/v1")
public class PermitAll {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailServiceImpl userDetailService;
    @Autowired
    private ProductServiceImpl productService;


    @PostMapping("/auth/sign-up")
    public ResponseEntity<RegistrationResponse> register(@RequestBody UserRequest userRequest) {
        UsersEntity user = userDetailService.add(userRequest);
        RegistrationResponse response = new RegistrationResponse("register successful !");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }



    @PostMapping("/auth/sign-in")
    public ResponseEntity getToken(@RequestBody TokenRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            UserDetailsAdapter userDetailsAdapter = (UserDetailsAdapter) userDetails; // Ép kiểu UserDetails sang UserDetailsAdapter

            String token = jwtUtil.generateToken(userDetails);

            TokenResponse tokenResponse = new TokenResponse();
            tokenResponse.setUserId(userDetailsAdapter.getUserId()); // Lấy userId từ UserDetailsAdapter
            tokenResponse.setUserName(userDetailsAdapter.getUsername());
            tokenResponse.setToken(token);

            // Lấy danh sách roles từ UserDetailsAdapter
            List<String> roles = userDetailsAdapter.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
            tokenResponse.setRoles(roles);

            return new ResponseEntity(tokenResponse, HttpStatus.OK);
        } catch (AuthenticationException a){
            throw  new BaseException("RA-00-401");
        }
    }

    @GetMapping("/products/search")
    public ResponseEntity<List<ProductResponse>> findProductByName(@RequestParam("searchName") String searchName) {
        List<ProductResponse> productList = productService.findProduct(searchName);
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductResponse>> findProductsAndSort(
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size,
            @RequestParam(value = "sort") String sortBy) {
        List<ProductResponse> productList = productService.findProductAndSort(page, size, sortBy);
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @PostMapping("/shopping-cart")
    public String get() {
        return "HOME API";
    }


}

