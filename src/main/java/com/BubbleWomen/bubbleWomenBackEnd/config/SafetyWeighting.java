package com.BubbleWomen.bubbleWomenBackEnd.config;

import com.BubbleWomen.bubbleWomenBackEnd.service.RatingService;
import com.graphhopper.routing.ev.*;
import com.graphhopper.routing.weighting.AbstractWeighting;
import com.graphhopper.routing.weighting.TurnCostProvider;
import com.graphhopper.util.EdgeIteratorState;
import com.graphhopper.util.PMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component
public class SafetyWeighting extends AbstractWeighting {
    private final DecimalEncodedValue speedEnc;
    private final BooleanEncodedValue accessEnc;
    private final RatingService ratingService;

    private static final double MAX_RATING = 5.0;
    private static final double MIN_PRIORITY = 0.1;
    private static final double MAX_PRIORITY = 1.0;


    protected SafetyWeighting(EncodedValueLookup lookup, PMap pMap, RatingService ratingService) {
        
        super(lookup.getBooleanEncodedValue(VehicleAccess.key("car")),
                lookup.getDecimalEncodedValue(VehicleSpeed.key("car")),
                TurnCostProvider.NO_TURN_COST_PROVIDER);

        this.speedEnc = lookup.getDecimalEncodedValue(VehicleSpeed.key("car"));
        this.accessEnc = lookup.getBooleanEncodedValue(VehicleAccess.key("car"));
        this.ratingService = ratingService;
    }

    @Override
    public double calcMinWeightPerDistance() {
        return 0;
    }

    @Override
    public double calcEdgeWeight(EdgeIteratorState edge, boolean reverse) {
        if (!edge.get(accessEnc)) {
            return Double.POSITIVE_INFINITY;
        }

        double speed = edge.get(speedEnc);
        if (speed == 0) {
            return Double.POSITIVE_INFINITY;
        }

        double distance = edge.getDistance();
        String edgeId = String.valueOf(edge.getEdge());
        double safetyRating = ratingService.getAverageRating(edgeId);
        double priority = calculatePriority(safetyRating);

        return distance / (speed * priority);
    }

    private double calculatePriority(double safetyRating) {
        if (safetyRating == 0) return MIN_PRIORITY;
        return MIN_PRIORITY + (safetyRating / MAX_RATING) * (MAX_PRIORITY - MIN_PRIORITY);
    }

    @Override
    public String getName() {
        return "safety";
    }

    @Override
    public boolean hasTurnCosts() {
        return false;
    }

}
