package com.BubbleWomen.bubbleWomenBackEnd.repo;

import com.BubbleWomen.bubbleWomenBackEnd.model.UserLocation;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LocationRepository extends MongoRepository<UserLocation, String> {
    @Query("{ 'location' : { $near : { $geometry: { type: 'Point', coordinates: ?0 }, $maxDistance: ?1 } } }")
    List<UserLocation> findUsersNear(double[] location, double maxDistance);

    @Query("{ 'location' : { $geoWithin : { $centerSphere: [ [?0, ?1], ?2 ] } } }")
    List<UserLocation> findLocationNear(double longitude, double latitude, double radius);
}
