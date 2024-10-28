package com.BubbleWomen.bubbleWomenBackEnd.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "edgeRating")
public class EdgeRating {
    @Id
    private String id; // This could be the edge ID or a generated ID
    private String edgeId; // The unique edge ID
    private int[] ratingFrequency; // Array to store frequency of ratings from 0 to 5
    private double averageRating; // Average rating of the edge

    public EdgeRating(String edgeId) {
        this.edgeId = edgeId;
        this.ratingFrequency = new int[6]; // Initialize frequency for ratings 0-5
        this.averageRating = 0.0;
    }

    // Getters and setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEdgeId() {
        return edgeId;
    }

    public void setEdgeId(String edgeId) {
        this.edgeId = edgeId;
    }

    public int[] getRatingFrequency() {
        return ratingFrequency;
    }

    public void setRatingFrequency(int[] ratingFrequency) {
        this.ratingFrequency = ratingFrequency;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }
}
