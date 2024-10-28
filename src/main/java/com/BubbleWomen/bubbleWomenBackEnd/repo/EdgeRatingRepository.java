package com.BubbleWomen.bubbleWomenBackEnd.repo;

import com.BubbleWomen.bubbleWomenBackEnd.model.EdgeRating;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EdgeRatingRepository extends MongoRepository<EdgeRating, String> {
    Optional<EdgeRating> findByEdgeId(String edgeId);
}
