package com.BubbleWomen.bubbleWomenBackEnd.service;

import com.BubbleWomen.bubbleWomenBackEnd.model.UserLocation;
import com.BubbleWomen.bubbleWomenBackEnd.repo.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserLocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private FCMService fcmService;

    public void updateUserToken(String userId, String fcmToken) {
        UserLocation userLocation = locationRepository.findByUserId(userId);
//                .orElse(new UserLocation());
        userLocation.setUserId(userId);
        userLocation.setFcmToken(fcmToken);
        locationRepository.save(userLocation);
    }

    public UserLocation saveUserLocation(String userId, String fcmToken, double longitude, double latitude) {
        UserLocation userLocation = new UserLocation(userId, fcmToken, longitude, latitude);
        return locationRepository.save(userLocation);
    }

    public List<UserLocation> findNearbyUsers(String currentUserId, double longitude, double latitude, double maxDistanceKm) {
        double maxDistanceMeters = maxDistanceKm * 1000;
        double[] coordinates = new double[]{longitude, latitude};

        List<UserLocation> nearbyUsers = locationRepository.findUsersNear(coordinates, maxDistanceMeters);

        // Filter out the current user
        nearbyUsers = nearbyUsers.stream()
                .filter(user -> !user.getUserId().equals(currentUserId))
                .collect(Collectors.toList());

        // Send notifications to nearby users
        List<String> nearbyTokens = nearbyUsers.stream()
                .map(UserLocation::getFcmToken)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (!nearbyTokens.isEmpty()) {
            fcmService.sendNotificationToMultipleTokens(
                    nearbyTokens,
                    "New User Nearby!",
                    "Someone is in your vicinity"
            );
        }

        return nearbyUsers;
    }
}
