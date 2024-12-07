package com.example.demo.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.Entity.*;
import org.bson.types.ObjectId;

@Repository


public interface UserRepository extends MongoRepository<User, ObjectId> {
    //MongoRepository<User, String>

    // Custom query methods if needed
    User findByBarcode(String barcode);
}
