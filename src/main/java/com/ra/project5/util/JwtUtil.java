package com.ra.project5.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {      //Json Web Token
    private final long EXPIRED = (1000 * 60 * 2);
    private final String JWT_KEY = "RikkeiAcademySecretKey";

    public String generateToken(UserDetails userDetails) {
        Date now = new Date();
        Date expired = new Date(now.getTime() + EXPIRED);
        String token = Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(expired)
                .signWith(SignatureAlgorithm.HS512, generateKey(JWT_KEY))
                .compact();
        return token;
    }

    public String getUsername(String token) {   // lấy thông tin user trong chuỗi token
        String username = Jwts.parser()
                .setSigningKey(generateKey(JWT_KEY))
                .parseClaimsJws(token)
                .getBody().getSubject();
        return username;
    }

    public boolean validateToken(String token) {     //nhận một token và kiểm tra xem token có hợp lệ không.
        try {
            Jwts.parser().setSigningKey(generateKey(JWT_KEY)).parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    private String generateKey(String key) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            // Đưa chuỗi và digest để tạo băm
            byte[] hashedBytes = digest.digest(key.getBytes());
            // Tạo secret key từ byte[] đã băm
            SecretKeySpec secretKeySpec = new SecretKeySpec(hashedBytes, "ASE");
            // Chuyển key thành chuỗi base 64
            String base64Key = Base64.getEncoder().encodeToString(secretKeySpec.getEncoded());
            System.out.println("SECRET_KEY: " + base64Key);
            return base64Key;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
