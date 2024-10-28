package com.BubbleWomen.bubbleWomenBackEnd.service;

import com.BubbleWomen.bubbleWomenBackEnd.model.LocationPost;
import com.BubbleWomen.bubbleWomenBackEnd.repo.LocationPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocationPostService {

    @Autowired
    private LocationPostRepository locationPostRepository;

    public LocationPost saveLocationPost(String userId, String postId, double longitude, double latitude, String message) {
        LocationPost post = new LocationPost(userId, postId, new GeoJsonPoint(latitude, longitude), message);
        return locationPostRepository.save(post);
    }

    public List<LocationPost> findNearbyPosts(String currentUserId, double longitude, double latitude, double maxDistanceKm) {
        double maxDistanceMeters = maxDistanceKm * 1000;
        double[] coordinates = new double[]{latitude, longitude};

        List<LocationPost> nearbyPosts = locationPostRepository.findPostsNear(coordinates, maxDistanceMeters);
        System.out.println(nearbyPosts);
        // Filter out the current user
        nearbyPosts = nearbyPosts.stream()
                .filter(user -> !user.getUserId().equals(currentUserId))
                .collect(Collectors.toList());

        return nearbyPosts;
    }
}
