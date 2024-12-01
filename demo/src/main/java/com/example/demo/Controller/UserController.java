package com.example.demo.Controller;

import io.jsonwebtoken.Claims;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Util.JWT_util;

@RestController
public class UserController {

    @GetMapping("/generate")
    public String generateToken(@RequestParam String username) {
        // 使用 JwtUtil 的 generateToken 方法
        return JWT_util.generateToken(username);
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam String token) {
        try {
            // 使用 JwtUtil 的 parseToken 方法
            Claims claims = JWT_util.parseToken(token);
            return "Token valid. User: " + claims.getSubject();
        } catch (Exception e) {
            return "Invalid token: " + e.getMessage();
        }
    }
}
