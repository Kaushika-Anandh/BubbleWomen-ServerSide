package com.BubbleWomen.bubbleWomenBackEnd.service;

import com.BubbleWomen.bubbleWomenBackEnd.model.LocationPost;
import com.BubbleWomen.bubbleWomenBackEnd.repo.LocationPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationPostService {

    @Autowired
    private LocationPostRepository locationPostRepository;

    public LocationPost saveLocationPost( double longitude, double latitude, String message, String time, String hashtag) {
        LocationPost post = new LocationPost(new GeoJsonPoint(latitude, longitude), message, time, hashtag);
        return locationPostRepository.save(post);
    }

    public List<LocationPost> findNearbyPosts(double longitude, double latitude, double maxDistanceKm) {
        double maxDistanceMeters = maxDistanceKm * 1000;
        double[] coordinates = new double[]{latitude, longitude};

        List<LocationPost> nearbyPosts = locationPostRepository.findPostsNear(coordinates, maxDistanceMeters);
        System.out.println(nearbyPosts);

        return nearbyPosts;
    }
}
