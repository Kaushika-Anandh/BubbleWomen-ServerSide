package com.BubbleWomen.bubbleWomenBackEnd.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FCMTokenService {
    @Autowired
    private FirebaseMessaging firebaseMessaging;

//    public FCMTokenService(FirebaseMessaging firebaseMessaging) {
//        this.firebaseMessaging = firebaseMessaging;
//    }

    public String generateToken() {
        try {
            // Generate registration token
            String token = UUID.randomUUID().toString(); // This is a simplified version
            System.out.println(token);
            // Validate token structure
            Message message = Message.builder()
                    .setToken(token)
                    .build();

            // Test token by sending a silent message
            firebaseMessaging.send(message);

            return token;

        } catch (FirebaseMessagingException e) {
            System.out.println(e);
            throw new RuntimeException("Failed to generate FCM token", e);
        }
    }
}
