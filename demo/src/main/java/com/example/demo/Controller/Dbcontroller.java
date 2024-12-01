package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.example.demo.Entity.*;
import com.example.demo.Service.*;


@RestController
@RequestMapping("/api/users")
public class Dbcontroller {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{barcode}")
    public User getUserByBarcode(@PathVariable String barcode) {
        System.out.println("barcode: " + barcode);

        return userService.getUserByBarcode(barcode);
    }
    // @GetMapping
    // public User getUserByBarcode(@RequestParam("barcode") String barcode) {
    //     return userService.getUserByBarcode(barcode);
    // }

    @PostMapping
    public User saveUser(@RequestBody User user) {
        return userService.saveUser(user);
    }
}
