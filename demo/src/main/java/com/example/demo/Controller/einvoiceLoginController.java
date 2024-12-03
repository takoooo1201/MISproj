package com.example.demo.Controller;

import com.example.demo.Service.LoginService;
import org.springframework.web.bind.annotation.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.Map;
import java.util.TreeMap;

@RestController
@RequestMapping("/api")
public class einvoiceLoginController {
    
    private final LoginService loginService;

    // Constructor Injection
    public einvoiceLoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public Response login(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        // Decode Base64-encoded username and password
        String decodedUsername = new String(Base64.getDecoder().decode(username), StandardCharsets.UTF_8);
        String decodedPassword = new String(Base64.getDecoder().decode(password), StandardCharsets.UTF_8);

        System.out.println("Decoded Username: " + decodedUsername);
        System.out.println("Decoded Password: " + decodedPassword);


        try {
            // Call the service function (if needed for further logic)
            String status=loginService.performApiCall(decodedUsername, decodedPassword);//,timeStamp,signature);

            return new Response(status);
        } catch (Exception e) {
            // Handle exceptions gracefully
            return new Response("Error generating signature: " + e.getMessage());
        }
    }

    public static class LoginRequest {
        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public static class Response {
        private String message;
        
        public Response(String message) {
            this.message = message;
            
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}


