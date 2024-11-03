package com.BubbleWomen.bubbleWomenBackEnd.DTOModel;

public class LocationNearbyDTO {
    private double longitude;
    private double latitude;
    private double maxDistanceKm;

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getMaxDistanceKm() {
        return maxDistanceKm;
    }

    public void setMaxDistanceKm(double maxDistanceKm) {
        this.maxDistanceKm = maxDistanceKm;
    }
}
