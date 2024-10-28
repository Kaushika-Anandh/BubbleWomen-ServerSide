package com.BubbleWomen.bubbleWomenBackEnd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoutingService {
    private static final String GRAPHHOPPER_URL = "http://localhost:8989/route";
    private final RatingService ratingService;
    private final RestTemplate restTemplate;

    @Autowired
    public RoutingService(RatingService ratingService) {
        this.ratingService = ratingService;
        this.restTemplate = new RestTemplate();
    }
    public Map<String, Object> findSafestRoute(double fromLat, double fromLon, double toLat, double toLon) {
        // Build the URL with parameters
        String url = String.format("%s?point=%f,%f&point=%f,%f&profile=car&instructions=true&points_encoded=false",
                GRAPHHOPPER_URL, fromLat, fromLon, toLat, toLon);

        // Make request to GraphHopper
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        Map<String, Object> routeResponse = response.getBody();

        // Process the response and add safety information
        if (routeResponse != null && routeResponse.containsKey("paths")) {
            List<Map<String, Object>> paths = (List<Map<String, Object>>) routeResponse.get("paths");
            if (!paths.isEmpty()) {
                Map<String, Object> path = paths.get(0);

                // Get the edges from the path
                List<Map<String, Object>> edges = (List<Map<String, Object>>) path.get("snapped_waypoints");

                // Add safety information for each edge
                List<Map<String, Object>> safetyInfo = new ArrayList<>();
                for (Map<String, Object> edge : edges) {
                    String edgeId = edge.get("edge_id").toString();
                    double safetyRating = ratingService.getAverageRating(edgeId);

                    Map<String, Object> edgeInfo = new HashMap<>();
                    edgeInfo.put("edgeId", edgeId);
                    edgeInfo.put("safetyRating", safetyRating);
                    safetyInfo.add(edgeInfo);
                }

                path.put("safety_info", safetyInfo);
            }
        }

        return routeResponse;
    }
}
