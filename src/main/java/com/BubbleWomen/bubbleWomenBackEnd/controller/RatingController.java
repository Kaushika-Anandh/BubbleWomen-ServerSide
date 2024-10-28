package com.BubbleWomen.bubbleWomenBackEnd.controller;

import com.BubbleWomen.bubbleWomenBackEnd.model.EdgeRating;
import com.BubbleWomen.bubbleWomenBackEnd.service.RatingService;
import com.graphhopper.util.EdgeIteratorState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ratings")
public class RatingController {
    @Autowired
    private RatingService ratingService;

    @PostMapping("/coordinates")
    public ResponseEntity<Void> addRating(
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam int rating) {

        ratingService.addRating(latitude, longitude, rating);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{edgeId}")
    public ResponseEntity<EdgeRating> getEdgeRating(@PathVariable String edgeId) {
        EdgeRating edgeRating = ratingService.getEdgeRating(edgeId);
        return edgeRating != null ? ResponseEntity.ok(edgeRating) : ResponseEntity.notFound().build();
    }
}
