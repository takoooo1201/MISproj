package com.example.demo.Controller;

import com.example.demo.Service.EinvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/einvoice")
public class einvoiceController {

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
