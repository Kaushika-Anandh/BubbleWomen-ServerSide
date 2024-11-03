package com.BubbleWomen.bubbleWomenBackEnd.service;

import com.BubbleWomen.bubbleWomenBackEnd.model.UserLocation;
import com.BubbleWomen.bubbleWomenBackEnd.repo.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserLocationService {

    @Autowired
    private LocationRepository locationRepository;

//    @Autowired
//    private FCMService fcmService;

    public void updateUserToken(String userId, String expoToken) {
        UserLocation userLocation = locationRepository.findByUserId(userId);
//                .orElse(new UserLocation());
        userLocation.setUserId(userId);
        userLocation.setExpoToken(expoToken);
        locationRepository.save(userLocation);
    }

    public UserLocation saveUserLocation(String userId, String expoToken, double longitude, double latitude) {
        if (!locationRepository.findByExpoToken(expoToken).isEmpty()){
            return null;
        }
        UserLocation userLocation = new UserLocation(userId, expoToken, longitude, latitude);
        return locationRepository.save(userLocation);
    }
//
    public List<UserLocation> findNearbyUsers(double longitude, double latitude, double maxDistanceKm) {
        double maxDistanceMeters = maxDistanceKm * 1000;
        double[] coordinates = new double[]{longitude, latitude};

        List<UserLocation> nearbyUsers = locationRepository.findUsersNear(coordinates, maxDistanceMeters);

        // Send notifications to nearby users
//        List<String> nearbyTokens = nearbyUsers.stream()
//                .map(UserLocation::getExpoToken)
//                .filter(Objects::nonNull)
//                .collect(Collectors.toList());

//        if (!nearbyTokens.isEmpty()) {
//            fcmService.sendNotificationToMultipleTokens(
//                    nearbyTokens,
//                    "New User Nearby!",
//                    "Someone is in your vicinity"
//            );
//        }

        return nearbyUsers;
    }
}
