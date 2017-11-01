package com.tracker.lantimat.cartracker.mapActivity.models;

import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

/**
 * Created by GabdrakhmanovII on 11.10.2017.
 */

public class Track {
    long carId;
    boolean engineOn;
    GeoPoint geoPoint;
    double speed;
    @ServerTimestamp
    Date timestamp;

    public Track() {
    }

    public Track(long carId, boolean isEngineOn, GeoPoint geoPoint, double speed, Date timestamp) {
        this.carId = carId;
        this.engineOn = isEngineOn;
        this.geoPoint = geoPoint;
        this.speed = speed;
        this.timestamp = timestamp;
    }

    public long getCarId() {
        return carId;
    }

    public void setCarId(long carId) {
        this.carId = carId;
    }

    public boolean isEngineOn() {
        return engineOn;
    }

    public void setEngineOn(boolean engineOn) {
        this.engineOn = engineOn;
    }

    public GeoPoint getGeoPoint() {
        return geoPoint;
    }

    public void setGeoPoint(GeoPoint geoPoint) {
        this.geoPoint = geoPoint;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(long speed) {
        this.speed = speed;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
