package com.BubbleWomen.bubbleWomenBackEnd.repo;

import com.BubbleWomen.bubbleWomenBackEnd.model.LocationPost;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface LocationPostRepository extends MongoRepository<LocationPost, String> {

    @Query("{ 'location': { $nearSphere: { $geometry: { type: 'Point', coordinates: ?0 }, $maxDistance: ?1 } } }")
    List<LocationPost> findPostsNear(double[] coordinates, double maxDistance);

}
