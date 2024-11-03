package com.BubbleWomen.bubbleWomenBackEnd.controller;

import com.BubbleWomen.bubbleWomenBackEnd.DTOModel.LocationNearbyDTO;
import com.BubbleWomen.bubbleWomenBackEnd.DTOModel.RatingDTO;
import com.BubbleWomen.bubbleWomenBackEnd.model.NodeRating;
import com.BubbleWomen.bubbleWomenBackEnd.service.NodeRatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("api/ratings")
@RequiredArgsConstructor
public class RatingController {
    @Autowired
    private NodeRatingService nodeRatingService;

    @PostMapping("/rate")
    public ResponseEntity<Void> addRating(
            @RequestBody RatingDTO input){
        nodeRatingService.saveLocationRating(input.getLatitude(), input.getLongitude(), input.getRating());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/averageNearbyRating")
    public ResponseEntity<Double> getAverageNearbyRating(
            @RequestBody LocationNearbyDTO input) {
        // Find nearby ratings
        List<NodeRating> nearbyRatings = nodeRatingService.
                findNearbyRatings(input.getLongitude(), input.getLatitude(), input.getMaxDistanceKm());
        // Calculate the average rating
//        System.out.println(nearbyRatings);
        double averageRating = nodeRatingService.calculateAverageRating(nearbyRatings);

        // Return the average rating as a response entity
        return new ResponseEntity<>(averageRating, HttpStatus.OK);
    }

}
