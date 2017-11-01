package com.tracker.lantimat.cartracker.mapActivity.models;

import com.google.firebase.firestore.GeoPoint;

/**
 * Created by GabdrakhmanovII on 27.10.2017.
 */

public class Cars {

    private int id;
    private int driverId;
    private String name;
    private String carNumber;
    private String ptsNumber;
    private Track track;

    public Cars() {
    }

    public Cars(int id, int driverId, String name, String carNumber, String ptsNumber) {
        this.id = id;
        this.driverId = driverId;
        this.name = name;
        this.carNumber = carNumber;
        this.ptsNumber = ptsNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getPtsNumber() {
        return ptsNumber;
    }

    public void setPtsNumber(String ptsNumber) {
        this.ptsNumber = ptsNumber;
    }

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }
}
