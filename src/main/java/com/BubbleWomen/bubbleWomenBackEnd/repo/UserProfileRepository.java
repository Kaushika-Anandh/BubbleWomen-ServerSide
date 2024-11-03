package com.BubbleWomen.bubbleWomenBackEnd.repo;

import com.BubbleWomen.bubbleWomenBackEnd.model.UserProfile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends MongoRepository<UserProfile,String> {
}
