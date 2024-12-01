package com.example.demo;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JWT_test {

    // 定義一個簡單的密鑰
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // 生成 Token
    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username) // 主題
                .setIssuer("TestApp") // 簽發者
                .setIssuedAt(new Date()) // 簽發時間
                .setExpiration(new Date(System.currentTimeMillis() + 60000)) // 過期時間 (60秒)
                .claim("role", "admin") // 自定義數據
                .signWith(SECRET_KEY) // 使用密鑰簽名
                .compact(); // 生成 Token
    }

    // 解析 Token
    public static Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY) // 使用相同密鑰驗證
                .build()
                .parseClaimsJws(token) // 解析 Token
                .getBody(); // 獲取 Claims
    }

    public static void main(String[] args) {
        // 1. 測試生成 Token
        String token = generateToken("testUser");
        System.out.println("生成的 Token: " + token);

        // 2. 測試解析 Token
        Claims claims = parseToken(token);
        System.out.println("解析後的數據:");
        System.out.println("用戶名: " + claims.getSubject());
        System.out.println("簽發者: " + claims.getIssuer());
        System.out.println("角色: " + claims.get("role"));
        System.out.println("過期時間: " + claims.getExpiration());
    }
}
