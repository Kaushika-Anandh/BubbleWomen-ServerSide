package com.BubbleWomen.bubbleWomenBackEnd.config;

import com.BubbleWomen.bubbleWomenBackEnd.service.RatingService;
import com.graphhopper.config.Profile;
import com.graphhopper.routing.WeightingFactory;
import com.graphhopper.routing.ev.EncodedValueLookup;
import com.graphhopper.routing.weighting.Weighting;
import com.graphhopper.util.PMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component
public class SafetyWeightingFactory implements WeightingFactory {
    private final RatingService ratingService;

    @Autowired
    public SafetyWeightingFactory(RatingService ratingService) {
        this.ratingService = ratingService;
    }

//    @Override
//    public Weighting createWeighting(Profile profile, PMap hints, EncodedValueLookup lookup) {
//        return new SafetyWeighting(lookup, hints, ratingService);
//    }

    @Override
    public Weighting createWeighting(Profile profile, PMap pMap, boolean b) {
        return null;
    }
}
