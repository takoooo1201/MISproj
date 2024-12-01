package com.example.demo.Service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.*;

@Service
public class LoginService {
    public String performApiCall(String username, String password,String timeStamp, String signature) {
        // Step 2: Make the POST request to '/invoice-api/PB2CAPIVAN/Carrier/Aggregate'
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
        bodyParams.put("appID", "EINV7202407292089");//EINV7202407292089
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

            // if (response.getBody() != null && response.getBody().containsKey("details")) {
            //     return filterInvoiceDetails((List<Map<String, Object>>) response.getBody().get("details"));
            // }

        if (response != null ){//&& "執行成功".equals(responseData.msg)) {
            // Success: Navigate to home or perform the desired action
            System.out.println("response: " + response);
            System.out.println("response code: " + response.getBody().get("code"));


            navigateToHome();
            return response.getBody().get("code").toString();
        } else {
            String errorMessage = "登入失敗: " ;//+ (responseData != null ? responseData.getMsg() : "Unknown error");
            // Handle error accordingly
            System.out.println("response: " + errorMessage);
            
            System.err.println(errorMessage);
            return response.getBody().get("code").toString();
            
        }
    }

// } catch (Exception e) {
// System.err.println("Error during API call: " + e.getMessage());
// // Handle exception accordingly
// }
    
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
