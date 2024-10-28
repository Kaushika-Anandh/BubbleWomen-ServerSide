package com.BubbleWomen.bubbleWomenBackEnd.controller;

import com.BubbleWomen.bubbleWomenBackEnd.service.RoutingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/route")
public class RouteController {
    private final RoutingService routingService;

    @Autowired
    public RouteController(RoutingService routingService) {
        this.routingService = routingService;
    }

    @GetMapping("/safest-route")
    public ResponseEntity<Map<String, Object>> getSafestRoute(
            @RequestParam double fromLat,
            @RequestParam double fromLon,
            @RequestParam double toLat,
            @RequestParam double toLon) {
        try {
            Map<String, Object> route = routingService.findSafestRoute(fromLat, fromLon, toLat, toLon);
            return ResponseEntity.ok(route);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }
}
