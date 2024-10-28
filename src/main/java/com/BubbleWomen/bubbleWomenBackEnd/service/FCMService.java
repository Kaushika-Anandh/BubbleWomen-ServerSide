package com.BubbleWomen.bubbleWomenBackEnd.service;

import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FCMService {

    @Autowired
    private FirebaseMessaging firebaseMessaging;

    public void sendNotification(String fcmToken, String title, String body) {
        try {
            Message message = Message.builder()
                    .setNotification(Notification.builder()
                            .setTitle(title)
                            .setBody(body)
                            .build())
                    .setToken(fcmToken)
                    .build();

            String response = firebaseMessaging.send(message);
            System.out.println("Successfully sent message: " + response);
        } catch (FirebaseMessagingException e) {
            System.err.println("Failed to send notification: " + e.getMessage());
        }
    }

    public void sendNotificationToMultipleTokens(List<String> tokens, String title, String body) {
        MulticastMessage message = MulticastMessage.builder()
                .setNotification(Notification.builder()
                        .setTitle(title)
                        .setBody(body)
                        .build())
                .addAllTokens(tokens)
                .build();

        try {
            BatchResponse response = firebaseMessaging.sendMulticast(message);
            System.out.println("Successfully sent messages: " + response.getSuccessCount());
        } catch (FirebaseMessagingException e) {
            System.err.println("Failed to send notifications: " + e.getMessage());
        }
    }
}
