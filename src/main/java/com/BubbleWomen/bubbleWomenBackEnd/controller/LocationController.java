package com.BubbleWomen.bubbleWomenBackEnd.controller;

import com.BubbleWomen.bubbleWomenBackEnd.model.LocationPost;
import com.BubbleWomen.bubbleWomenBackEnd.model.UserLocation;
import com.BubbleWomen.bubbleWomenBackEnd.service.JWTService;
import com.BubbleWomen.bubbleWomenBackEnd.service.LocationPostService;
import com.BubbleWomen.bubbleWomenBackEnd.service.RatingService;
import com.BubbleWomen.bubbleWomenBackEnd.service.UserLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/location")
@RequiredArgsConstructor
public class LocationController {

    @Autowired
    private UserLocationService userLocationService;

    @Autowired
    private LocationPostService locationPostService;

    @PostMapping("blog/save")
    public ResponseEntity<LocationPost> saveLocationPost(
            @RequestParam String userId,
            @RequestParam String postId,
            @RequestParam double longitude,
            @RequestParam double latitude,
            @RequestParam String message
    ){
        try {
            LocationPost post = locationPostService.saveLocationPost(
                    userId, postId, longitude, latitude, message);
            return ResponseEntity.ok(post);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("blog/nearby")
    public ResponseEntity<List<LocationPost>> findNearbyPosts(
            @RequestParam String currentUserId,
            @RequestParam double longitude,
            @RequestParam double latitude,
            @RequestParam double maxDistanceKm) {
        try {
            List<LocationPost> nearbyUsers = locationPostService.findNearbyPosts(
                    currentUserId,
                    longitude,
                    latitude,
                    maxDistanceKm
            );
            return ResponseEntity.ok(nearbyUsers);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/save")
    public ResponseEntity<UserLocation> saveLocation(
            @RequestParam String userId,
            @RequestParam String fcmToken,
            @RequestParam double longitude,
            @RequestParam double latitude) {
        try {
            UserLocation location = userLocationService.saveUserLocation(
                    userId, fcmToken, longitude, latitude);
            return ResponseEntity.ok(location);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/nearby")
    public ResponseEntity<List<UserLocation>> findNearbyUsers(
            @RequestParam String currentUserId,
            @RequestParam double longitude,
            @RequestParam double latitude,
            @RequestParam double maxDistanceKm) {
        try {
            List<UserLocation> nearbyUsers = userLocationService.findNearbyUsers(
                    currentUserId,
                    longitude,
                    latitude,
                    maxDistanceKm
            );
            return ResponseEntity.ok(nearbyUsers);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
