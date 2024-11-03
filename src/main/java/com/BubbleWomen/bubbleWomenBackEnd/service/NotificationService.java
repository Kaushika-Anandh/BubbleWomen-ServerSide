package com.BubbleWomen.bubbleWomenBackEnd.service;

import com.BubbleWomen.bubbleWomenBackEnd.DTOModel.LocationNearbyDTO;
import com.BubbleWomen.bubbleWomenBackEnd.model.UserLocation;
import com.BubbleWomen.bubbleWomenBackEnd.repo.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class NotificationService {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private UserLocationService userLocationService;

    private final String EXPO_PUSH_ENDPOINT = "https://exp.host/--/api/v2/push/send";

    public void sendPushNotification(String title, String message) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            String token = "ExponentPushToken[PWc-E7Ghru5BtFBxgvUr-_]"; // Ensure this is a valid token
            Map<String, Object> body = Map.of(
                    "to", token,
                    "title", title,
                    "body", message
            );

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(EXPO_PUSH_ENDPOINT, request, String.class);

            // Log the response from the Expo server
            System.out.println("Response from Expo: " + response.getBody());

            // Check for errors in the response
            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("Notification sent successfully.");
            } else {
                System.out.println("Failed to send notification. Status code: " + response.getStatusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendSOSPushNotification(LocationNearbyDTO input) {
        List<UserLocation> nearbyUsers = userLocationService
                .findNearbyUsers(input.getLongitude(), input.getLatitude(), input.getMaxDistanceKm());
        for (UserLocation user:nearbyUsers){
            try{
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            String token = user.getExpoToken(); // Ensure this is a valid token
            Map<String, Object> body = Map.of(
                    "to", token,
                    "title", "SOS",
                    "body", "Coordinates: Latitude: " + String.valueOf(input.getLatitude())
                            +" Longitude: "+ String.valueOf(input.getLongitude())
            );

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(EXPO_PUSH_ENDPOINT, request, String.class);

            // Log the response from the Expo server
            System.out.println("Response from Expo: " + response.getBody());

            // Check for errors in the response
            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("Notification sent successfully.");
            } else {
                System.out.println("Failed to send notification. Status code: " + response.getStatusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        }

    }
}
