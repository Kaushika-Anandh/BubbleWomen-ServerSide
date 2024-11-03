package com.BubbleWomen.bubbleWomenBackEnd.controller;

import com.BubbleWomen.bubbleWomenBackEnd.DTOModel.LocationNearbyDTO;
import com.BubbleWomen.bubbleWomenBackEnd.DTOModel.LocationPostDTO;
import com.BubbleWomen.bubbleWomenBackEnd.DTOModel.UserLocationDTO;
import com.BubbleWomen.bubbleWomenBackEnd.model.LocationPost;
import com.BubbleWomen.bubbleWomenBackEnd.model.UserLocation;
import com.BubbleWomen.bubbleWomenBackEnd.service.LocationPostService;
import com.BubbleWomen.bubbleWomenBackEnd.service.UserLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/location")
@RequiredArgsConstructor
public class LocationController {

    @Autowired
    private UserLocationService userLocationService;

    @Autowired
    private LocationPostService locationPostService;

    @PostMapping("blog/save")
    public ResponseEntity<LocationPost> saveLocationPost(
            @RequestBody LocationPostDTO input){
        try {
            LocationPost post = locationPostService.saveLocationPost(
                    input.getLongitude(), input.getLatitude(), input.getMessage());
            return ResponseEntity.ok(post);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("blog/nearby")
    public ResponseEntity<List<LocationPost>> findNearbyPosts(
            @RequestBody LocationNearbyDTO input) {
        try {
            List<LocationPost> nearbyUsers = locationPostService.findNearbyPosts(
                    input.getLongitude(), input.getLatitude(), input.getMaxDistanceKm());
            return ResponseEntity.ok(nearbyUsers);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("user/save")
    public ResponseEntity<UserLocation> saveLocation(
            @RequestBody UserLocationDTO input) {
        try {
            UserLocation location = userLocationService.saveUserLocation(
                    input.getUserId(), input.getExpoToken(), input.getLongitude(), input.getLatitude());
            return ResponseEntity.ok(location);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("user/nearby")
    public ResponseEntity<List<UserLocation>> findNearbyUsers(@RequestBody LocationNearbyDTO input) {
        try {
            List<UserLocation> nearbyUsers = userLocationService.findNearbyUsers(
                    input.getLongitude(), input.getLatitude(), input.getMaxDistanceKm());
            return ResponseEntity.ok(nearbyUsers);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
