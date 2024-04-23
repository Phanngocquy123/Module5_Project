package com.ra.project5.controller;

import com.ra.project5.model.dto.request.UserRequest;
import com.ra.project5.model.entity.UsersEntity;
import com.ra.project5.model.token.TokenRequest;
import com.ra.project5.model.token.TokenResponse;
import com.ra.project5.service.impl.UserDetailServiceImpl;
import com.ra.project5.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class PermitAll {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailServiceImpl userDetailService;

    @PostMapping("/sign-up")
    public UsersEntity register(@RequestBody UserRequest userRequest) {

        // Trả về thông tin của người dùng đã đăng ký thành công
        return userDetailService.add(userRequest);
    }

    @PostMapping("/auth/sign-in")
    public ResponseEntity getToken(@RequestBody TokenRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtUtil.generateToken(userDetails);

        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setToken(token);

        return new ResponseEntity(tokenResponse, HttpStatus.OK);
    }
}

