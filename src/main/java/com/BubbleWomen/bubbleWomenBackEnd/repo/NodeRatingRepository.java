package com.BubbleWomen.bubbleWomenBackEnd.repo;

import com.BubbleWomen.bubbleWomenBackEnd.model.NodeRating;
import com.BubbleWomen.bubbleWomenBackEnd.model.UserLocation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NodeRatingRepository extends MongoRepository<NodeRating, String> {

    @Query("{ 'location': { $nearSphere: { $geometry: { type: 'Point', coordinates: ?0 }, $maxDistance: ?1 } } }")
    List<NodeRating> findRatingsNear(double[] coordinates, double maxDistanceMeters);

}
