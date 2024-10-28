package com.BubbleWomen.bubbleWomenBackEnd.controller;

import com.BubbleWomen.bubbleWomenBackEnd.service.FCMTokenService;
import com.BubbleWomen.bubbleWomenBackEnd.service.UserLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.Console;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/fcm")
@RequiredArgsConstructor
public class FCMController {

    @Autowired
    private FCMTokenService fcmTokenService;

    @Autowired
    private UserLocationService userLocationService;



    @PostMapping("/token/generate")
    public ResponseEntity<Map<String, String>> generateToken() {
        System.out.println("here");
        try {
            String token = fcmTokenService.generateToken();
            Map<String, String> response = new HashMap<>();
            response.put("fcmToken", token);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/token/register")
    public ResponseEntity<Void> registerToken(
            @RequestParam String userId,
            @RequestParam String fcmToken,
            @RequestParam(required = false) Double longitude,
            @RequestParam(required = false) Double latitude) {
        try {
            if (longitude != null && latitude != null) {
                userLocationService.saveUserLocation(userId, fcmToken, longitude, latitude);
            } else {
                // Save only token without location
                userLocationService.updateUserToken(userId, fcmToken);
            }
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
