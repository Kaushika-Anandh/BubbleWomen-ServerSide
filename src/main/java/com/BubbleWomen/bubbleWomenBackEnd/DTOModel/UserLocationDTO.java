package com.BubbleWomen.bubbleWomenBackEnd.DTOModel;

public class UserLocationDTO {
    private double longitude;
    private double latitude;
    private String expoToken;
    private String userId;

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

    public String getExpoToken() {
        return expoToken;
    }

    public void setExpoToken(String expoToken) {
        this.expoToken = expoToken;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
