package com.example.demo.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.Entity.*;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    // Custom query methods if needed
    User findByBarcode(String barcode);
}
