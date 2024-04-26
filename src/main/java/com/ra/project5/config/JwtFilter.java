package com.ra.project5.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ra.project5.model.token.TokenResponse;
import com.ra.project5.model.token.UserDetailsAdapter;
import com.ra.project5.service.impl.UserDetailServiceImpl;
import com.ra.project5.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.GrantedAuthority;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String token = request.getHeader("Authorization");
////        if (token != null && token.startsWith("Bearer Token ")) {
////            String jwtToken = token.substring(13);
////            if (jwtUtil.validateToken(jwtToken)) {
////                String username = jwtUtil.getUsername(jwtToken);
////                try {
////                    // Dùng userDetailsService để lấy thông tin user
////                    UserDetails userDetails = userDetailService.loadUserByUsername(username);
////                    if (userDetails instanceof UserDetailsAdapter) {
////                        UserDetailsAdapter userDetailsAdapter = (UserDetailsAdapter) userDetails;
////                        // Create a new TokenResponse object
////                        TokenResponse tokenResponse = new TokenResponse();
////
////                        tokenResponse.setUserName(userDetailsAdapter.getUsername());
////                        tokenResponse.setToken(jwtToken);
////
////                        // Retrieve roles from UserDetailsAdapter and set them in TokenResponse
////                        List<String> roles = userDetailsAdapter.getAuthorities().stream()
////                                .map(GrantedAuthority::getAuthority)
////                                .collect(Collectors.toList());
////                        tokenResponse.setRoles(roles);
////
////                        // Gửi đối tượng TokenResponse về cho client dưới dạng JSON
////                        response.setContentType("application/json");
////                        response.setCharacterEncoding("UTF-8");
////                        PrintWriter out = response.getWriter();
////                        ObjectMapper mapper = new ObjectMapper();
////                        String tokenResponseJson = mapper.writeValueAsString(tokenResponse);
////                        out.print(tokenResponseJson);
////                        out.flush();
////                        return;
////                    } else {
////                        throw new UsernameNotFoundException("Invalid user details type");
////                    }
////                } catch (UsernameNotFoundException ex) {
////                    // Log the exception or handle it as needed
////                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
////                    return;
////                }
////            }
////        }
////
////        filterChain.doFilter(request, response);
////    }

        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            String jwtToken = token.substring(7);
            if (jwtUtil.validateToken(jwtToken)) {
                String username = jwtUtil.getUsername(jwtToken);
                // Dùng userDetailsService để lấy thông tin user
                UserDetails userDetails = userDetailService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
