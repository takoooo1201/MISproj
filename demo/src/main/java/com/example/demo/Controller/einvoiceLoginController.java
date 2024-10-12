package com.example.demo.Controller;


import org.springframework.web.bind.annotation.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;
import java.util.TreeMap;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.time.Instant;

@RestController
@RequestMapping("api")
public class einvoiceLoginController {
    @PostMapping("/login")
    public Response login(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        // 假設進行一些驗證，例如簡單地檢查帳號和密碼是否符合某個條件
        // if ("admin".equals(username) && "password".equals(password)) {
        //     return new Response("Login successful");
        // } else {
        //     return new Response("Invalid username or password");
        // }
        Map<String, String> params = new TreeMap<String, String>();
        long currentTimeInSeconds = Instant.now().getEpochSecond();
        String timeStamp = Long.toString(currentTimeInSeconds + 30);
        params.put("version", "1.0");
        params.put("serial", "0000000003");
        params.put("action", "qryCarrierAgg");
        params.put("cardType", "3J0002");
        params.put("cardNo", username);
        params.put("cardEncrypt", password);
        params.put("appID", "EINV7202407292089");
        params.put("timeStamp", timeStamp);
        params.put("uuid", "0004");

        String apiKey = "QVYyYTNkVDRscHdBZFZlbQ==";
        
        try {
            Map<String, String> sortedParams = new TreeMap<>(params);

            // Construct the query string with UTF-8 encoding
            StringBuilder queryString = new StringBuilder();
            for (Map.Entry<String, String> entry : sortedParams.entrySet()) {
                if (queryString.length() > 0) {
                    queryString.append("&");
                }
                queryString.append(entry.getKey())
                           .append("=")
                           .append(entry.getValue());
            }

            // Step 2: Apply HMAC-SHA256 with the API key
            Mac sha256Hmac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(apiKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            sha256Hmac.init(secretKey);

            byte[] hmacBytes = sha256Hmac.doFinal(queryString.toString().getBytes(StandardCharsets.UTF_8));

            // Step 3: Encode the signature in Base64
            String signature = Base64.getEncoder().encodeToString(hmacBytes);
            System.out.println("Generated Signature: " + signature);
            System.out.println("Generated time: " + timeStamp);
            return new Response("OK" ,signature,timeStamp);
            //return new Response("ok",signature,timeStamp);
        } catch (Exception e) {
            // Handle the exception, perhaps logging it and returning an error response
            return new Response("Error generating signature: " + e.getMessage(),"","");
        }
    }

    public static class LoginRequest {
        private String username;
        private String password;

        // Getters and Setters
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
        String signature;
        String timeStamp;
        String message;
    
        public Response(String message, String signature, String timeStamp) {
            this.message = message;
            this.signature = signature;
            this.timeStamp = timeStamp;
        }
    
        public String getMessage() {
            return message;
        }
    
        public void setMessage(String message) {
            this.message = message;
        }
    
        public String getSignature() {
            return signature;
        }
    
        public void setSignature(String signature) {
            this.signature = signature;
        }
    
        public String getTimeStamp() {
            return timeStamp;
        }
    
        public void setTimeStamp(String timeStamp) {
            this.timeStamp = timeStamp;
        }
    }
}
