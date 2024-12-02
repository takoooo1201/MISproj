package com.example.demo.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.example.demo.Repository.*;
import com.example.demo.Entity.*;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserByBarcode(String barcode) {
        return userRepository.findByBarcode(barcode);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }
    public String getVerifyCodeByBarcode(String barcode) {
        System.out.println("barcode: " + barcode);
    
        User user = getUserByBarcode(barcode);
        if (user != null) {
            return user.getVerifyCode(); // Assumes `User` has a `getVerifyCode` method
        } else {
            return "No user found for the given barcode";
        }
    }
}
