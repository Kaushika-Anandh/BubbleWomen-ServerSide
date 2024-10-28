package com.BubbleWomen.bubbleWomenBackEnd.service;
import com.BubbleWomen.bubbleWomenBackEnd.model.EdgeRating;
import com.BubbleWomen.bubbleWomenBackEnd.repo.EdgeRatingRepository;
import com.graphhopper.GraphHopper;
import com.graphhopper.routing.util.EdgeFilter;
import com.graphhopper.storage.index.LocationIndex;
import com.graphhopper.storage.index.Snap;
import com.graphhopper.util.EdgeIteratorState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingService {
    @Autowired
    private EdgeRatingRepository edgeRatingRepository;

//    @Autowired
    private GraphHopper graph; // Inject your GraphHopper graph here

//    @Autowired
    private LocationIndex locationIndex; // Inject your LocationIndex here

    public void addRating(double latitude, double longitude, int rating) {
        String edgeId = getEdgeIdFromCoordinates(latitude, longitude);
        if (edgeId != null) {
            EdgeRating edgeRating = edgeRatingRepository.findByEdgeId(edgeId)
                    .orElseGet(() -> {
                        // Initialize a new EdgeRating with zero frequency array
                        EdgeRating newEdgeRating = new EdgeRating(edgeId);
                        newEdgeRating.setRatingFrequency(new int[6]); // Assuming ratings are from 0 to 5
                        return newEdgeRating;
                    });

            // Update frequency and calculate the new average
            edgeRating.getRatingFrequency()[rating]++;
            edgeRating.setAverageRating(calculateAverage(edgeRating));

            edgeRatingRepository.save(edgeRating);
        } else {
            // Handle the case where the edge ID could not be found
            throw new RuntimeException("Could not find edge for the given coordinates.");
        }
    }

    public String getEdgeIdFromCoordinates(double latitude, double longitude) {
        // Get the closest edge on the road
        Snap snap = locationIndex.findClosest(longitude, latitude, EdgeFilter.ALL_EDGES);
        EdgeIteratorState closestEdge = snap.getClosestEdge(); // Retrieve the closest edge
        int edgeId = closestEdge != null ? closestEdge.getEdge() : -1; // Get the edge ID
        return edgeId != -1 ? String.valueOf(edgeId) : null; // Return the edge ID as a string
    }

    private double calculateAverage(EdgeRating edgeRating) {
        int totalRatings = 0;
        int totalWeight = 0;
        for (int i = 0; i < edgeRating.getRatingFrequency().length; i++) {
            totalRatings += edgeRating.getRatingFrequency()[i];
            totalWeight += i * edgeRating.getRatingFrequency()[i];
        }
        return totalRatings > 0 ? (double) totalWeight / totalRatings : 0.0; // Avoid division by zero
    }

    public EdgeRating getEdgeRating(String edgeId) {
        return edgeRatingRepository.findByEdgeId(edgeId).orElse(null);
    }

    public double getAverageRating(String edgeId) {
        return edgeRatingRepository.findByEdgeId(edgeId)
                .map(EdgeRating::getAverageRating)
                .orElse(1.0); // Default to 1.0 if no rating is found
    }
}
