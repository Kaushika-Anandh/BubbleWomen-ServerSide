package com.BubbleWomen.bubbleWomenBackEnd.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class FirebaseConfig {
    @Value("${firebase.credentials.path}")
    private String firebaseConfigPath;

    @PostConstruct
    public void initialize() {
        try {
            // Validate that the config file exists
            File configFile = new File(firebaseConfigPath);
            if (!configFile.exists()) {
                throw new IllegalStateException(
                        "Firebase configuration file does not exist at path: " + firebaseConfigPath
                );
            }

            FileInputStream serviceAccount = new FileInputStream(configFile);

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            // Check if Firebase is already initialized
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                System.out.println("Firebase has been initialized successfully");
            } else {
                System.out.println("Firebase is already initialized");
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize Firebase: " + e.getMessage(), e);
        }
    }
    @Bean
    FirebaseMessaging firebaseMessaging() {
        return FirebaseMessaging.getInstance();
    }
}