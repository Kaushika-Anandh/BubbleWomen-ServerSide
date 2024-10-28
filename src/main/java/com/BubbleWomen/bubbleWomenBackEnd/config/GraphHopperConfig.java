package com.BubbleWomen.bubbleWomenBackEnd.config;

import com.graphhopper.GraphHopper;
import com.graphhopper.config.Profile;

import com.graphhopper.routing.ev.EncodedValueLookup;

import com.graphhopper.routing.util.EncodingManager;
import com.graphhopper.routing.weighting.custom.CustomWeighting;
import com.graphhopper.util.PMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

//@Configuration
public class GraphHopperConfig {
    @Autowired
    private SafetyWeightingFactory safetyWeightingFactory;

    @Bean
    public GraphHopper graphHopper() {
        GraphHopper hopper = new GraphHopper();

        // Clean existing cache
//        hopper.clean();

        // Set paths
        hopper.setOSMFile("C:/Users/nithi/graphhopper/southern-zone-latest.osm.pbf");
        hopper.setGraphHopperLocation("C:/Users/nithi/graphhopper/graph-cache");

        // Create and add the car profile - using "car" instead of "car_profile"
        Profile profile = new Profile("car")  // Changed from "car_profile" to "car"
                .setWeighting("fastest");

        hopper.setProfiles(Arrays.asList(profile));

        // Import and create graph
        hopper.importOrLoad();

        return hopper;
    }

    @Bean
    public EncodedValueLookup encodedValueLookup(GraphHopper graphHopper) {
        return graphHopper.getEncodingManager();
    }

    @Bean
    public PMap graphHopperPMap() {
        return new PMap();
    }
}
