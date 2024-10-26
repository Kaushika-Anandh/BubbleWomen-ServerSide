package com.BubbleWomen.bubbleWomenBackEnd.repo;

import com.BubbleWomen.bubbleWomenBackEnd.model.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

public interface UserRepository extends MongoRepository <Users, String> {
    Users findByUsername(String username);
}