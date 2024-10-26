package com.BubbleWomen.bubbleWomenBackEnd.service;

import com.BubbleWomen.bubbleWomenBackEnd.model.UserLocation;
import com.BubbleWomen.bubbleWomenBackEnd.repo.LocationRepository;
import org.springframework.data.geo.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserLocationService {

    @Autowired
    private LocationRepository locationRepository;

    public UserLocation saveUserLocation(String userId, double longitude, double latitude) {
        UserLocation currLocation = new UserLocation(userId, longitude, latitude);
        return locationRepository.save(currLocation);
    }

    public List<UserLocation> findNearbyUsers(double longitude, double latitude, double maxDistanceKm){
        System.out.println(longitude + " " + latitude + " " + maxDistanceKm);
//        Point currentLocation = new Point(longitude, latitude);
//        Distance distance = new Distance(maxDistanceKm, Metrics.KILOMETERS);
        double[] currentLocation = new double[]{longitude, latitude};
        return locationRepository.findUsersNear(currentLocation, maxDistanceKm);
    }

    public List<UserLocation> findNearbyUsersV2(){
        return locationRepository.findAll();
    }
}
