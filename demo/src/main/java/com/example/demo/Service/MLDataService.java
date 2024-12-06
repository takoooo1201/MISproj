package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.example.demo.Service.JWTService;

import java.time.Instant;
import java.util.*;
import com.example.demo.Entity.User;
@Service
public class MLDataService {
    @Autowired
    private UserService userService;

    @Autowired
    private JWTService jwtService;
    private final String apiUrl = "https://api.einvoice.nat.gov.tw/PB2CAPIVAN/invServ/InvServ";
    
    @Value("${app.id}")
    private String appID;

    public Map<String, Object> fetchInvoiceDetails(InvoiceRequest request) throws Exception {
        String bar=this.jwtService.validateToken(request.getToken());
        System.out.println("bar"+bar);
        String password = this.userService.getVerifyCodeByBarcode(bar);
        System.out.println("pass"+password);

        //added
        User user = this.userService.getUserByBarcode(bar);
        String userId = user.get_id();
        System.out.println("User ID: " + userId);

        String startDate = formatDate(request.getStartDate());
        String endDate = formatDate(request.getEndDate());

        long currentTimestamp = Instant.now().getEpochSecond();
        String timeStamp = String.valueOf(currentTimestamp + 10);

        Map<String, String> bodyParams = new LinkedHashMap<>();
        bodyParams.put("version", "0.6");
        bodyParams.put("cardType", "3J0002");
        bodyParams.put("cardNo", bar);
        bodyParams.put("expTimeStamp", "2147483647");
        bodyParams.put("action", "carrierInvChk");
        bodyParams.put("timeStamp", timeStamp);
        bodyParams.put("startDate", startDate);
        bodyParams.put("endDate", endDate);
        bodyParams.put("onlyWinningInv", "N");
        bodyParams.put("uuid", "0007");
        bodyParams.put("appID", appID);
        bodyParams.put("cardEncrypt", password);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        String body = convertMapToUrlEncodedString(bodyParams);

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, Map.class);

        if (response.getBody() != null && response.getBody().containsKey("details")) {
            List<Map<String, Object>> details = (List<Map<String, Object>>) response.getBody().get("details");
            
            
            for (Map<String, Object> detail : details) {
                String invNum = (String) detail.get("invNum");
                Map<String, Integer> invDate = (Map<String, Integer>) detail.get("invDate");
                String formattedDate = formatTaiwanDate(invDate);

                List<Map<String, Object>> items = fetchInvoiceItemDetails(invNum, formattedDate);
                //added
                detail.keySet().retainAll(Collections.singleton("invNum"));
                detail.put("userId", userId);
                detail.put("items", items);
                
            }
        }
        return response.getBody();
    }

    private List<Map<String, Object>> fetchInvoiceItemDetails(String invNum, String invDate) {
        try {
            long currentTimestamp = Instant.now().getEpochSecond();
            String timeStamp = String.valueOf(currentTimestamp + 10);

            Map<String, String> bodyParams = new LinkedHashMap<>();
            bodyParams.put("version", "0.5");
            bodyParams.put("cardType", "3J0002");
            bodyParams.put("cardNo", "/YM7CBKZ");
            bodyParams.put("expTimeStamp", "2147483647");
            bodyParams.put("action", "carrierInvDetail");
            bodyParams.put("timeStamp", timeStamp);
            bodyParams.put("invNum", invNum);
            bodyParams.put("invDate", invDate);
            bodyParams.put("uuid", "0007");
            bodyParams.put("appID", "EINV7202407292089");
            bodyParams.put("cardEncrypt", "Tacopeko@7781");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            String body = convertMapToUrlEncodedString(bodyParams); 

            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<String> entity = new HttpEntity<>(body, headers);

            ResponseEntity<Map> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, Map.class);

            if (response.getBody() != null && response.getBody().containsKey("details")) {
                return filterInvoiceDetails((List<Map<String, Object>>) response.getBody().get("details"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

    private List<Map<String, Object>> filterInvoiceDetails(List<Map<String, Object>> details) {
        List<Map<String, Object>> filtered = new ArrayList<>();
        for (Map<String, Object> detail : details) {
            //detail.remove("amount");
            //detail.remove("rowNum");
            //detail.remove("unitPrice");
            //detail.remove("amount");
            
            double unitPrice = Double.parseDouble((String) detail.get("unitPrice"));
            double amount = Double.parseDouble((String) detail.get("amount"));
            if (unitPrice > 0 && amount >= 0) {
                detail.keySet().retainAll(Set.of("description", "quantity"));
                filtered.add(detail);
            }
        }
        return filtered;
    }

    private String formatDate(String dateString) {
        return dateString.replace("-", "/");
    }

    private String formatTaiwanDate(Map<String, Integer> invDate) {
        int year = invDate.get("year") + 1911;
        int month = invDate.get("month");
        int date = invDate.get("date");

        return String.format("%04d/%02d/%02d", year, month, date);
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

    public static class InvoiceRequest {
        private String startDate;
        private String endDate;
        private String username;
        private String password;
        private String token;


        // Getters and Setters
        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

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
        //localStorage.getItem('jwtToken');
        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
