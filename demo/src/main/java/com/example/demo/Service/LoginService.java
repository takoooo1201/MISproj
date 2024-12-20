package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import com.example.demo.Entity.User;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

@Service
public class LoginService {
    @Value("${app.key}")
    private String appKey;

    @Value("${app.id}")
    private String appID;

    @Autowired
    private UserService userService;

    @Autowired
    private JWTService jwtService;

    public String performApiCall(String username, String password){//,String timeStamp, String signature) {
        Map<String, String> params = new TreeMap<String, String>();
        long currentTimeInSeconds = Instant.now().getEpochSecond();
        String timeStamp = Long.toString(currentTimeInSeconds +20);
        String signature="";

        params.put("version", "1.0");
        params.put("serial", "0000000003");
        params.put("action", "qryCarrierAgg");
        params.put("cardType", "3J0002");
        params.put("cardNo", username);
        params.put("cardEncrypt", password);
        params.put("appID", appID);
        params.put("timeStamp", timeStamp);
        params.put("uuid", "0004");

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
            SecretKeySpec secretKeySpec = new SecretKeySpec(appKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            sha256Hmac.init(secretKeySpec);

            byte[] hmacBytes = sha256Hmac.doFinal(queryString.toString().getBytes(StandardCharsets.UTF_8));

            // Encode the signature in Base64
            signature = Base64.getEncoder().encodeToString(hmacBytes);

            System.out.println("Generated Signature: " + signature);
            System.out.println("Generated Timestamp: " + timeStamp);

        } catch (Exception e) {
            // Handle exceptions gracefully
            System.out.println("error in generate signature");
        }
        
        //Make the POST request to '/invoice-api/PB2CAPIVAN/Carrier/Aggregate'
        String url = "https://api.einvoice.nat.gov.tw/PB2CAPIVAN/Carrier/Aggregate";

        //MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        Map<String, String> bodyParams = new LinkedHashMap<>();
        bodyParams.put("version", "1.0");
        bodyParams.put("serial", "0000000003");
        bodyParams.put("action", "qryCarrierAgg");
        bodyParams.put("cardType", "3J0002");
        bodyParams.put("cardNo", username);
        bodyParams.put("cardEncrypt", password);
        bodyParams.put("timeStamp", timeStamp);
        bodyParams.put("uuid", "0004");
        bodyParams.put("appID", appID);//EINV7202407292089
        bodyParams.put("signature", signature);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String pay = convertMapToUrlEncodedString(bodyParams);
        //String pay = bodyParams.toString();

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> entity = new HttpEntity<>(pay, headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                url, HttpMethod.POST, entity, Map.class
        );
        System.out.println("whole response: " + response);
        if (response.getBody().get("code").toString().equals("200") ){//&& "執行成功".equals(responseData.msg)) {
            // Success: Navigate to home or perform the desired action
            if(response.getBody().get("code")=="200"){
                System.out.println("haha:"+response.getBody().get("code"));
            }
            System.out.println("response: " + response);
            System.out.println("response code: " + response.getBody().get("code"));

            User existingUser = userService.getUserByBarcode(username);
            String token = jwtService.generateToken(username);

            if (existingUser == null) {
                User newUser = new User();
                newUser.setBarcode(username);
                newUser.setVerifyCode(password);
                newUser.setLastVerifiedAt(LocalDateTime.now());
                newUser.setToken(token);
                newUser.setCardNo("-1");

                userService.saveUser(newUser);
                System.out.println("New user created: " + newUser);
            } else {
                existingUser.setToken(token);
                userService.saveUser(existingUser);
                System.out.println("User token updated: " + existingUser);
            }


            navigateToHome();
            //return response.getBody().get("code").toString();
            return token;
        } else {
            String errorMessage = "登入失敗: " ;//+ (responseData != null ? responseData.getMsg() : "Unknown error");
            // Handle error accordingly
            System.out.println("response: " + errorMessage);
            
            System.out.println(response.getBody().get("code").toString());
            //return response.getBody().get("code").toString();
            return "error";
            
        }
    }
    
    private String convertMapToUrlEncodedString(Map<String, String> params) {
        StringBuilder encodedString = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (encodedString.length() > 0) {
                encodedString.append("&");
            }
            encodedString.append(entry.getKey()).append("=").append(entry.getValue());
        }
        return encodedString.toString();
    }
    private void navigateToHome() {
        // Implement your navigation logic here
        System.out.println("Navigating to home...");
    }
}
