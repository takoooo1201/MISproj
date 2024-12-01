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
}
