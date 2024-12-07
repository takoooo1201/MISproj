package com.example.demo.Entity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import org.bson.types.ObjectId;

@Document(collection = "Users")
public class User {

    @Id
    private ObjectId _id;
    private String id; // Maps to "_id"
    private String barcode; // Maps to "Barcode"
    private String VerifyCode; // Maps to "VerifyCode"
    private LocalDateTime lastVerifiedAt; // Maps to "lastVerifiedAt"
    private String token; // Maps to "token"
    private String cardNo;
    // Getters and setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getVerifyCode() {
        return VerifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.VerifyCode = verifyCode;
    }

    public LocalDateTime getLastVerifiedAt() {
        return lastVerifiedAt;
    }

    public void setLastVerifiedAt(LocalDateTime lastVerifiedAt) {
        this.lastVerifiedAt = lastVerifiedAt;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    public String get_id() {
        return _id!=null?_id.toHexString():null;
    }
    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }
}

