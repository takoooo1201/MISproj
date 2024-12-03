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
            return ResponseEntity.ok(einvoiceService.fetchInvoiceDetails(request));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching invoice details: " + e.getMessage());
        }
        
    }

}
