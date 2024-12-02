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

        Map<String, String> params = new TreeMap<String, String>();
        long currentTimeInSeconds = Instant.now().getEpochSecond();
        String timeStamp = Long.toString(currentTimeInSeconds );

        params.put("version", "1.0");
        params.put("serial", "0000000003");
        params.put("action", "qryCarrierAgg");
        params.put("cardType", "3J0002");
        params.put("cardNo", decodedUsername);
        params.put("cardEncrypt", decodedPassword);
        params.put("appID", "EINV7202407292089");
        params.put("timeStamp", timeStamp);
        params.put("uuid", "0004");

        String apiKey = "QVYyYTNkVDRscHdBZFZlbQ==";
        //String apiKey = "QVYyYTNkVDRscHdBZFZlbQ==";


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
            
            // Apply HMAC-SHA256 with the API key
            Mac sha256Hmac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(apiKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            sha256Hmac.init(secretKeySpec);

            byte[] hmacBytes = sha256Hmac.doFinal(queryString.toString().getBytes(StandardCharsets.UTF_8));

            // Encode the signature in Base64
            String signature = Base64.getEncoder().encodeToString(hmacBytes);

            System.out.println("Generated Signature: " + signature);
            System.out.println("Generated Timestamp: " + timeStamp);

            // Call the service function (if needed for further logic)
            String status=loginService.performApiCall(decodedUsername, decodedPassword,timeStamp,signature);

            return new Response(status, signature, timeStamp);
        } catch (Exception e) {
            // Handle exceptions gracefully
            return new Response("Error generating signature: " + e.getMessage(), "", "");
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
        private String signature;
        private String timeStamp;

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

// package com.example.demo.Controller;
// import com.example.demo.Service.LoginService;

// import org.springframework.web.bind.annotation.*;
// import java.nio.charset.StandardCharsets;
// import java.util.Base64;
// import java.util.Map;
// import java.util.TreeMap;
// import javax.crypto.Mac;
// import javax.crypto.spec.SecretKeySpec;
// import java.net.URLEncoder;
// import java.time.Instant;

// import javax.crypto.Cipher; // For AES encryption/decryption
// import javax.crypto.spec.SecretKeySpec; // For specifying the AES secret key


// @RestController
// @RequestMapping("api")
// public class einvoiceLoginController {
    
    
//     @PostMapping("/login")
//     public Response login(@RequestBody LoginRequest loginRequest) {
        



//         String username = loginRequest.getUsername();
//         String password = loginRequest.getPassword();
//         // Decode Base64-encoded username and password
//         //String decodedUsername = new String(Base64.getDecoder().decode(loginRequest.getUsername()), StandardCharsets.UTF_8);
//         //String decodedPassword = new String(Base64.getDecoder().decode(loginRequest.getPassword()), StandardCharsets.UTF_8);
//         // Secret key must be the same as the client-side
//         //String secret = "your-secret-key"; // Replace with your actual key (must be 16/24/32 bytes)

//         // Decrypt username and password
//         //String decryptedUsername = decryptAES(loginRequest.getUsername(), "your-secret-key");
//         //String decryptedPassword = decryptAES(loginRequest.getPassword(), "your-secret-key");

//         //System.out.println("Decoded Username: " + decryptedUsername);
//         //System.out.println("Decoded Password: " + decryptedPassword);

//         // try {
//         //     // Secret key must be the same as the client-side
//         //     String secretKey1 = "your-secret-keyy"; // Replace with your actual key (must be 16/24/32 bytes)
    
//         //     // Decrypt username and password
//         //     String decryptedUsername = decryptAES(loginRequest.getUsername(), secretKey1);
//         //     String decryptedPassword = decryptAES(loginRequest.getPassword(), secretKey1);
    
//         //     System.out.println("Decrypted Username: " + decryptedUsername);
//         //     System.out.println("Decrypted Password: " + decryptedPassword);
    
//         //     // Proceed with your logic
//         //     // ...
    
//         //     //return new Response("OK", "signature-placeholder", "timeStamp-placeholder");
//         // } catch (Exception e) {
//         //     // Handle exceptions gracefully
//         //     System.err.println("Error decrypting AES: " + e.getMessage());
//         //     return new Response("Error decrypting AES", "", "");
//         // }

//         String decodedUsername = new String(Base64.getDecoder().decode(loginRequest.getUsername()), StandardCharsets.UTF_8);
//         String decodedPassword = new String(Base64.getDecoder().decode(loginRequest.getPassword()), StandardCharsets.UTF_8);

//         System.out.println("Decoded Username: " + decodedUsername);
//         System.out.println("Decoded Password: " + decodedPassword);
//         Map<String, String> params = new TreeMap<String, String>();
//         long currentTimeInSeconds = Instant.now().getEpochSecond();
//         String timeStamp = Long.toString(currentTimeInSeconds + 30);
//         params.put("version", "1.0");
//         params.put("serial", "0000000003");
//         params.put("action", "qryCarrierAgg");
//         params.put("cardType", "3J0002");
//         params.put("cardNo", decodedUsername);//decryptedUsername);
//         params.put("cardEncrypt", decodedPassword);//decryptedPassword);
//         params.put("appID", "EINV7202407292089");
//         params.put("timeStamp", timeStamp);
//         params.put("uuid", "0004");

//         String apiKey = "QVYyYTNkVDRscHdBZFZlbQ==";
        
//         try {
//             Map<String, String> sortedParams = new TreeMap<>(params);

//             // Construct the query string with UTF-8 encoding
//             StringBuilder queryString = new StringBuilder();
//             for (Map.Entry<String, String> entry : sortedParams.entrySet()) {
//                 if (queryString.length() > 0) {
//                     queryString.append("&");
//                 }
//                 queryString.append(entry.getKey())
//                            .append("=")
//                            .append(entry.getValue());
//             }

//             // Step 2: Apply HMAC-SHA256 with the API key
//             Mac sha256Hmac = Mac.getInstance("HmacSHA256");
//             SecretKeySpec secretKey1 = new SecretKeySpec(apiKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
//             sha256Hmac.init(secretKey1);

//             byte[] hmacBytes = sha256Hmac.doFinal(queryString.toString().getBytes(StandardCharsets.UTF_8));

//             // Step 3: Encode the signature in Base64
//             String signature = Base64.getEncoder().encodeToString(hmacBytes);
//             System.out.println("Generated Signature: " + signature);
//             System.out.println("Generated time: " + timeStamp);

            
   


//             return new Response("OK" ,signature,timeStamp);
//             //return new Response("ok",signature,timeStamp);
//         } catch (Exception e) {
//             // Handle the exception, perhaps logging it and returning an error response
//             return new Response("Error generating signature: " + e.getMessage(),"","");
//         }
//     }

//     public static class LoginRequest {
//         private String username;
//         private String password;

//         // Getters and Setters
//         public String getUsername() {
//             return username;
//         }

//         public void setUsername(String username) {
//             this.username = username;
//         }

//         public String getPassword() {
//             return password;
//         }

//         public void setPassword(String password) {
//             this.password = password;
//         }
//     }

//     public static class Response {
//         String signature;
//         String timeStamp;
//         String message;
    
//         public Response(String message, String signature, String timeStamp) {
//             this.message = message;
//             this.signature = signature;
//             this.timeStamp = timeStamp;
//         }
    
//         public String getMessage() {
//             return message;
//         }
    
//         public void setMessage(String message) {
//             this.message = message;
//         }
    
//         public String getSignature() {
//             return signature;
//         }
    
//         public void setSignature(String signature) {
//             this.signature = signature;
//         }
    
//         public String getTimeStamp() {
//             return timeStamp;
//         }
    
//         public void setTimeStamp(String timeStamp) {
//             this.timeStamp = timeStamp;
//         }
//     }
// }
