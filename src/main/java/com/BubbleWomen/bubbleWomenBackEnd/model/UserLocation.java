package com.BubbleWomen.bubbleWomenBackEnd.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "userGeoLocation")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLocation {
    @Id
    private String id;

    @Field("userId")
    private String userId;

    @Field("fcmToken")
    private String fcmToken;

    // Changed to use GeoJSON Point type
    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private GeoJsonPoint location;

//    public UserLocation() {
//    }

    public UserLocation(String userId, String fcmToken , double longitude, double latitude) {
        this.userId = userId;
        this.fcmToken = fcmToken;
        this.location = new GeoJsonPoint(longitude, latitude);
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public GeoJsonPoint getLocation() {
        return location;
    }

    public void setLocation(GeoJsonPoint location) {
        this.location = location;
    }
}
