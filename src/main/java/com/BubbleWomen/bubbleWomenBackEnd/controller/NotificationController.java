package com.BubbleWomen.bubbleWomenBackEnd.controller;

import com.BubbleWomen.bubbleWomenBackEnd.DTOModel.LocationNearbyDTO;
import com.BubbleWomen.bubbleWomenBackEnd.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/sample")
    public void sendSample(
            @RequestParam String title,
            @RequestParam String message){
        notificationService.sendPushNotification(title, message);
    }

    @GetMapping("/SOS")
    public void sendSOS(
            @RequestBody LocationNearbyDTO input
            ){
        notificationService.sendSOSPushNotification(input);
    }
}
