package com.BubbleWomen.bubbleWomenBackEnd.service;

import com.BubbleWomen.bubbleWomenBackEnd.model.NodeRating;
import com.BubbleWomen.bubbleWomenBackEnd.repo.NodeRatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NodeRatingService {
    @Autowired
    private NodeRatingRepository nodeRatingRepository;

    public void saveLocationRating(double longitude, double latitude, int rating) {
        NodeRating rate = new NodeRating(rating, longitude, latitude);
        nodeRatingRepository.save(rate);
    }
    public List<NodeRating> findNearbyRatings(double longitude, double latitude, double maxDistanceKm) {
        double maxDistanceMeters = maxDistanceKm * 1000;
        double[] coordinates = new double[]{latitude, longitude};

        List<NodeRating> nearbyPosts = nodeRatingRepository.findRatingsNear(coordinates, maxDistanceMeters);
//        System.out.println(nearbyPosts);
        return nearbyPosts;
    }

    public double calculateAverageRating(List<NodeRating> ratings) {
        if (ratings == null || ratings.isEmpty()) {
            return 0;
        }
        double sum = ratings.stream()
                .mapToInt(NodeRating::getRating)
                .sum();

        return sum / ratings.size();
    }
}
