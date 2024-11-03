package com.BubbleWomen.bubbleWomenBackEnd.repo;

import com.BubbleWomen.bubbleWomenBackEnd.model.UserLocation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface LocationRepository extends MongoRepository<UserLocation, String> {
    @Query("{ 'location': { $nearSphere: { $geometry: { type: 'Point', coordinates: ?0 }, $maxDistance: ?1 } } }")
    List<UserLocation> findUsersNear(double[] location, double maxDistance);

    UserLocation findByUserId(String userId);

    Collection<Object> findByExpoToken(String expoToken);
}
