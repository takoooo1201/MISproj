package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.Util.JWT_util;

import io.jsonwebtoken.Claims;

import com.example.demo.Util.JWT_util;

import com.example.demo.Entity.*;
import com.example.demo.Service.*;

@RestController
@RequestMapping("/api/einvoice")
public class einvoiceController {
    @Autowired
    private UserService userService;
    @Autowired
    private EinvoiceService einvoiceService;

    @PostMapping("/fetchInvoiceDetails")
    public ResponseEntity<?> fetchInvoiceDetails(@RequestBody EinvoiceService.InvoiceRequest request) {
        
        try {
            //String password=getVerifyCodeByBarcode(getBarcode("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIvWU03Q0JLWiIsImlzcyI6IlRlc3RBcHAiLCJpYXQiOjE3MzMwOTU0NjEsImV4cCI6MTczMzA5OTA2MX0.tTcrZ2KN__wkscFsQQ8jRaL2K9jxB7eMQky8jz2YDtA"));
            return ResponseEntity.ok(einvoiceService.fetchInvoiceDetails(request));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching invoice details: " + e.getMessage());
        }
        
        
    }

    // public Boolean validateToken(@RequestParam String token) {
    //     try {
    //         // 使用 JwtUtil 的 parseToken 方法
    //         Claims claims = JWT_util.parseToken(token);
    //         return true;
    //     } catch (Exception e) {
    //         return false;
    //     }
    // }
    // public String getBarcode(@RequestParam String token) {
    //     try {
    //         // 使用 JwtUtil 的 parseToken 方法
    //         Claims claims = JWT_util.parseToken(token);
    //         return claims.getSubject();
    //     } catch (Exception e) {
    //         return "Invalid token: " + e.getMessage();
    //     }
    // }
    // public String getVerifyCodeByBarcode(String barcode) {
    //     System.out.println("barcode: " + barcode);
    
    //     User user = userService.getUserByBarcode(barcode);
    //     if (user != null) {
    //         return user.getVerifyCode(); // Assumes `User` has a `getVerifyCode` method
    //     } else {
    //         return "No user found for the given barcode";
    //     }
    // }

}
