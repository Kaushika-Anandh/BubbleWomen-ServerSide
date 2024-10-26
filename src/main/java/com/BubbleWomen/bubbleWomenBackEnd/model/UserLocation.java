package com.BubbleWomen.bubbleWomenBackEnd.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "userGeoLocation")
public class UserLocation {
    @Id
    private String id;

    @Field("userId")
    private String userId;

    @GeoSpatialIndexed
    private double[] location;

    public UserLocation(String userId, double longitude, double latitude) {
        this.userId = userId;
        this.location = new double[]{longitude, latitude};
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double[] getLocation() {
        return location;
    }

    public void setLocation(double[] location) {
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
