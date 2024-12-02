package com.example.demo.Service;

import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Util.JWT_util;

@Service
public class JWTService {

    public String generateToken(String username) {
        return JWT_util.generateToken(username);
    }

    // public Claims validateToken(String token) throws Exception {
    //     return JWT_util.parseToken(token);
    // }
    public String validateToken(@RequestParam String token) {
        try {
            // 使用 JwtUtil 的 parseToken 方法
            Claims claims = JWT_util.parseToken(token);
            return claims.getSubject();
        } catch (Exception e) {
            return "Invalid token: " + e.getMessage();
        }
    }
}
